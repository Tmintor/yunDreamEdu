package com.tminto.service;

import com.tminto.domain.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tminto.domain.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tminto
 * @since 2022-09-23
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
}
