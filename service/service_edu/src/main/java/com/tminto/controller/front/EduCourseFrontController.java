package com.tminto.controller.front;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.common.R;
import com.tminto.domain.EduCourse;
import com.tminto.domain.vo.CourseQueryVo;
import com.tminto.service.EduChapterService;
import com.tminto.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 吴员外
 * @date 2022/10/11 9:45
 */
@RestController
@RequestMapping("/eduservice/course/front")
public class EduCourseFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduChapterService eduChapterService;

    /**
     *  前后台共用获取课程接口
     * @CourseQueryVo 用于前台条件筛选查询
     */
    @PostMapping("/list/{current}/{limit}")
    public R getCourseList(@PathVariable Integer current,
                           @PathVariable Integer limit,
                           @RequestBody(required = false) CourseQueryVo searchObj) {
        Page<EduCourse> page = eduCourseService.getCourseList(current,limit,searchObj);
        Map<String, Object> map = BeanUtil.beanToMap(page, "records", "total", "size", "current");
        map.put("hasNext", page.hasNext());
        map.put("hasPrevious", page.hasPrevious());
        map.put("pages", page.getPages());
        return R.ok().data("list", map);
    }

    @GetMapping("{courseId}")
    public R getCourseDetail(@PathVariable String courseId) {

        //查询课程信息
        CourseWebVo courseWebVo = eduCourseService.getCourseDetail(courseId);

        //查询所有章节和小节
        List<Map<String, Object>> chapterVideoList = eduChapterService.getAllChapterVideoByCourseId(courseId);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList);
    }
}
