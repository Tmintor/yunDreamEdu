package com.tminto.controller;

import com.tminto.common.R;
import com.tminto.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author tminto
 * @since 2022-09-23
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("{courseId}")
    public R getAllChapterVideo(@PathVariable String courseId) {
        List<Map<String,Object>> list = eduChapterService.getAllChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

}

