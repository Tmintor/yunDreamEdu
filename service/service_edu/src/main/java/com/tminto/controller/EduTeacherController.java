package com.tminto.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.common.R;
import com.tminto.domain.EduCourse;
import com.tminto.domain.EduTeacher;
import com.tminto.domain.vo.TeachQuery;
import com.tminto.service.EduCourseService;
import com.tminto.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("list")
    public R getAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("item",list);
    }

    /**
     *  前台查询讲师
     */
    @GetMapping("/list/{current}/{limit}")
    public R getFamousTeacherList(@PathVariable Integer current, @PathVariable Integer limit) {
        Page<EduTeacher> page = eduTeacherService.page(current, limit, null);
        Map<String, Object> map = BeanUtil.beanToMap(page, "records", "total", "size", "current");
        map.put("hasNext", page.hasNext());
        map.put("hasPrevious", page.hasPrevious());
        return R.ok().data("page", map);
    }

    /**
     * 后台分页查询讲师
     */
    @PostMapping("list/{current}/{limit}")
    public R pageList(@PathVariable Integer current,
                      @PathVariable Integer limit,
                      @RequestBody TeachQuery teachQuery) {
        Page<EduTeacher> pageTeacher = eduTeacherService.page(current, limit,teachQuery);
        return R.ok().data("page",pageTeacher);
    }

    /**
     * 后台添加讲师
     */
    @PostMapping("")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean result = eduTeacherService.save(eduTeacher);
        return result ? R.ok() : R.error();
    }

    /**
     * 后台删除讲师
     */
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id) {
        boolean result = eduTeacherService.removeById(id);
        return result ? R.ok() : R.error();
    }

    /**
     *  后台根据id查看讲师信息
     */
    @GetMapping("{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    /**
     *  前台根据id查看讲师信息以及其课程信息
     */
    @GetMapping("/course/{id}")
    public R getDetailAndCourse(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",id);
        List<EduCourse> courseList = eduCourseService.list(courseQueryWrapper);
        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }


    @PutMapping("")
    public R modifyTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean result = eduTeacherService.updateById(eduTeacher);
        return result ? R.ok() : R.error();
    }


}

