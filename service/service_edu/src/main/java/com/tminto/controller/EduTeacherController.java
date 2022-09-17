package com.tminto.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.common.R;
import com.tminto.domain.EduTeacher;
import com.tminto.domain.vo.TeachQuery;
import com.tminto.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/edu/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("list")
    public R getAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("item",list);
    }

    @GetMapping("list/{current}/{limit}")
    public R pageList(@PathVariable Integer current,
                      @PathVariable Integer limit,
                      TeachQuery teachQuery) {
        Page<EduTeacher> pageTeacher = eduTeacherService.page(current, limit,teachQuery);
        return R.ok().data("page",pageTeacher);
    }

    @PostMapping("")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean result = eduTeacherService.save(eduTeacher);
        return result ? R.ok() : R.error();
    }

    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id) {
        boolean result = eduTeacherService.removeById(id);
        return result ? R.ok() : R.error();
    }

    @GetMapping("{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    @PutMapping("")
    public R modifyTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean result = eduTeacherService.updateById(eduTeacher);
        return result ? R.ok() : R.error();
    }



}

