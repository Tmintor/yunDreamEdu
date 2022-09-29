package com.tminto.controller;


import com.tminto.common.R;
import com.tminto.domain.EduVideo;
import com.tminto.feign.EduVodFeign;
import com.tminto.service.EduVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author tminto
 * @since 2022-09-23
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduVodFeign eduVodFeign;

    //添加小节
    @PostMapping("")
    public R addVideo(@RequestBody EduVideo video) {
        eduVideoService.save(video);
        return R.ok();
    }

    //删除小节，同时删除小节的视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {

        //TODO 分布式事务
        //获取视频id
        EduVideo video = eduVideoService.getById(id);
        //删除视频
        String vodId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(vodId))
        eduVodFeign.deleteVod(vodId);
        //删除小节
        eduVideoService.removeById(id);
        return R.ok();
    }



}

