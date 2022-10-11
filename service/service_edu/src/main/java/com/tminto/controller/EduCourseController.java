package com.tminto.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.common.R;
import com.tminto.domain.EduCourse;
import com.tminto.domain.vo.CourseInfoVo;
import com.tminto.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * @author tminto
 * @since 2022-09-23
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("/list/{current}/{limit}")
    public R getCourseList(@PathVariable Integer current, @PathVariable Integer limit) {
        Page<EduCourse> page = eduCourseService.getCoursePageList(current,limit);
        return R.ok().data("list", page);
    }

    @PostMapping("")
    public R addCourse(@RequestBody CourseInfoVo courseInfo) {
        String id = eduCourseService.addCourse(courseInfo);
        return R.ok().data("courseId", id);
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

    @GetMapping("/publish/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        Map<String, Object> map = eduCourseService.getPublishCourseInfo(id);
        return R.ok().data("publishCourse", map);
    }

    @PostMapping("/publish/{id}")
    public R submitCourse(@PathVariable String id) {
        eduCourseService.updateStatus(id);
        return R.ok();
    }

    @DeleteMapping("{id}")
    public R deleteCourseInfo(@PathVariable String id) {
        eduCourseService.deleteCourseInfo(id);
        return R.ok();
    }
}

