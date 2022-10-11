package com.tminto.controller;

import com.tminto.common.R;
import com.tminto.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 吴员外
 * @date 2022/10/3 14:23
 */
@RestController
@RequestMapping("/eduservice/index")
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping("/hot")
    public R getHotCourseTeacher() {

        Map<String, Object> hotTeacherAndCourse = indexService.getHotTeacherAndCourse();
        return R.ok().data(hotTeacherAndCourse);
    }


}
