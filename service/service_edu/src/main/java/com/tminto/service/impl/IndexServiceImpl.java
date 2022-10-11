package com.tminto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tminto.domain.EduCourse;
import com.tminto.domain.EduTeacher;
import com.tminto.mapper.EduCourseMapper;
import com.tminto.mapper.EduTeacherMapper;
import com.tminto.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴员外
 * @date 2022/10/3 15:18
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private EduTeacherMapper eduTeacherMapper;

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Override
    public Map<String, Object> getHotTeacherAndCourse() {

        //查询观看最多的前8个课程
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("view_count");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourses = eduCourseMapper.selectList(courseQueryWrapper);

        //查询等价最高的前四个讲师
        QueryWrapper<EduTeacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByAsc("level");
        teacherQueryWrapper.last("limit 4");
        List<EduTeacher> eduTeachers = eduTeacherMapper.selectList(teacherQueryWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("eduList", eduCourses);
        map.put("teacherList", eduTeachers);
        return map;
    }

}
