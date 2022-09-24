package com.tminto.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.tminto.domain.EduCourse;
import com.tminto.domain.EduCourseDescription;
import com.tminto.domain.vo.CourseInfoVo;
import com.tminto.mapper.EduCourseDescriptionMapper;
import com.tminto.mapper.EduCourseMapper;
import com.tminto.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tminto
 * @since 2022-09-23
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionMapper descriptionMapper;

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
}
