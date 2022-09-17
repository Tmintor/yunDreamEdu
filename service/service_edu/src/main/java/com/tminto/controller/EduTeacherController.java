package com.tminto.controller;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.common.R;
import com.tminto.domain.EduTeacher;
import com.tminto.service.EduTeacherService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/serviceedu/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("list")
    public R getAll() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("item",list);
    }

    @GetMapping("list/{current}/{limit}")
    public R pageList(@PathVariable Integer current, @PathVariable Integer limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        eduTeacherService.page(pageTeacher, null);
        return R.ok().data("page",pageTeacher);
    }

}

