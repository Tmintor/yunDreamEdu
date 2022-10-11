package com.tminto.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.controller.front.CourseWebVo;
import com.tminto.domain.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tminto.domain.vo.CourseInfoVo;
import com.tminto.domain.vo.CourseQueryVo;

import java.util.Map;

/**
 * @author tminto
 * @since 2022-09-23
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    Map<String, Object> getPublishCourseInfo(String id);

    void updateStatus(String id);

    Page<EduCourse> getCourseList(Integer current, Integer limit, CourseQueryVo courseQueryVo);

    void deleteCourseInfo(String id);

    Page<EduCourse> getCoursePageList(Integer current, Integer limit);

    CourseWebVo getCourseDetail(String id);
}
