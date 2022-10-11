package com.tminto.controller;

import com.tminto.common.R;
import com.tminto.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 吴员外
 * @date 2022/9/27 22:42
 */
@RestController
@RequestMapping("edu/vod")
public class VodController {

    @Autowired
    private VodService vodService;


    @PostMapping("/upLoad")
    public R upLoadVideo(MultipartFile file) {
        String videoId = vodService.upLoadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("{id}")
    public R deleteVod(@PathVariable String id) {
        vodService.deleteVideo(id);
        return R.ok();
    }

    @GetMapping("/PlayAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid) {
        String playAuth = vodService.getPlayAuth(vid);
        return R.ok().data("playAuth", playAuth);
    }


}
