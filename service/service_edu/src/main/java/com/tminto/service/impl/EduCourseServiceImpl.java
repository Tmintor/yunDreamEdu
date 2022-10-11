package com.tminto.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.controller.front.CourseWebVo;
import com.tminto.domain.EduChapter;
import com.tminto.domain.EduCourse;
import com.tminto.domain.EduCourseDescription;
import com.tminto.domain.EduVideo;
import com.tminto.domain.vo.CourseInfoVo;
import com.tminto.domain.vo.CourseQueryVo;
import com.tminto.feign.EduVodFeign;
import com.tminto.mapper.EduChapterMapper;
import com.tminto.mapper.EduCourseDescriptionMapper;
import com.tminto.mapper.EduCourseMapper;
import com.tminto.mapper.EduVideoMapper;
import com.tminto.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.PredicateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author tminto
 * @since 2022-09-23
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionMapper descriptionMapper;

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduVideoMapper eduVideoMapper;

    @Autowired
    private EduChapterMapper eduChapterMapper;

    @Autowired
    private EduVodFeign eduVodFeign;

    @Override
    public String addCourse(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtil.copyProperties(courseInfoVo, eduCourse);

        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new RuntimeException("添加课程失败");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        description.setId(eduCourse.getId());

        descriptionMapper.insert(description);

        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtil.copyProperties(eduCourse, courseInfoVo);

        //查询课程描述
        EduCourseDescription description = descriptionMapper.selectById(courseId);
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtil.copyProperties(courseInfoVo, eduCourse);
        int i = baseMapper.updateById(eduCourse);

        if (i == 0) {
            throw new RuntimeException("更新课程失败");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        descriptionMapper.updateById(description);
    }

    @Override
    public Map<String, Object> getPublishCourseInfo(String id) {
        Map<String, Object> map = eduCourseMapper.selectAllCourseInfo(id);
        return map;
    }

    @Override
    public void updateStatus(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        baseMapper.updateById(eduCourse);
    }

    @Override
    public Page<EduCourse> getCourseList(Integer current, Integer limit, CourseQueryVo courseQueryVo) {
        Page<EduCourse> page = new Page<>(current, limit);
        if (courseQueryVo == null) {
            baseMapper.selectPage(page, null);
        } else {
            QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())){//一级分类
                wrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
            }
            if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())){//二级分类
                wrapper.eq("subject_id",courseQueryVo.getSubjectId());
            }
            if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {//销量排序
                wrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {//时间排序
                wrapper.orderByDesc("gmt_create");
            }
            if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())) {//价格排序
                wrapper.orderByDesc("price");
            }
            baseMapper.selectPage(page,wrapper);
        }
        return page;
    }

    @Override
    public void deleteCourseInfo(String id) {
        //删除小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);

        List<String> vodIds = eduVideoMapper.selectAllVodIdByCourseId(id);
        //将集合中的null移除
        CollectionUtils.filter(vodIds, PredicateUtils.notNullPredicate());
        if (vodIds.size() > 0) {
            //将集合中的id通过逗号拼接成字符串
            String join = StringUtils.join(vodIds.toArray(), ",");
            eduVodFeign.deleteVod(join);
        }
        eduVideoMapper.delete(videoQueryWrapper);

        //删除章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        eduChapterMapper.delete(chapterQueryWrapper);

        //删除描述
        QueryWrapper<EduCourseDescription> descriptionQueryWrapper = new QueryWrapper<>();
        descriptionQueryWrapper.eq("id", id);
        descriptionMapper.delete(descriptionQueryWrapper);

        //删除课程
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("id", id);
        eduCourseMapper.delete(courseQueryWrapper);
    }

    @Override
    public Page<EduCourse> getCoursePageList(Integer current, Integer limit) {
        Page<EduCourse> page = new Page<>();
        baseMapper.selectPage(page, null);
        return page;
    }

    @Override
    public CourseWebVo getCourseDetail(String id) {

        return baseMapper.selectAllCourseDetail(id);
    }


}
