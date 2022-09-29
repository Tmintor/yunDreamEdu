package com.tminto.controller;

import com.tminto.common.R;
import com.tminto.domain.EduChapter;
import com.tminto.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    //课程大纲列表
    @GetMapping("chapterAndVideo/{courseId}")
    public R getAllChapterVideo(@PathVariable String courseId) {
        List<Map<String, Object>> list = eduChapterService.getAllChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }


    //添加章节
    @PostMapping("")
    public R addCharter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    @PutMapping("")
    public R updateChapterInfo(@RequestBody EduChapter chapter) {
        eduChapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        eduChapterService.deleteById(chapterId);
        return R.ok();
    }

}

