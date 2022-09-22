package com.tminto.controller;

import com.tminto.common.R;
import com.tminto.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 吴员外
 * @date 2022/9/21 20:32
 */
@RestController
@RequestMapping("/edu/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/upLoad")
    public R upLoadFile(MultipartFile multipartFile) {
        String url = ossService.fileUpLoad(multipartFile);
        return R.ok().data("url", url);
    }


}
