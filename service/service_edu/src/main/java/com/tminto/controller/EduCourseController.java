package com.tminto.controller;


import com.tminto.common.R;
import com.tminto.domain.EduSubject;
import com.tminto.domain.vo.CourseInfoVo;
import com.tminto.service.EduCourseService;
import com.tminto.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tminto
 * @since 2022-09-23
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("")
    public R addCourse(@RequestBody CourseInfoVo courseInfo) {
        String id = eduCourseService.addCourse(courseInfo);
        return R.ok().data("courseId",id);
    }

    @GetMapping("{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @PutMapping("")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfo) {

        eduCourseService.updateCourseInfo(courseInfo);

        return R.ok();
    }
}

