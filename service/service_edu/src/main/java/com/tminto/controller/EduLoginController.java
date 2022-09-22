package com.tminto.controller;

import com.tminto.common.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author 吴员外
 * @date 2022/9/20 17:03
 */
@RestController
@RequestMapping("/edu/user")
@CrossOrigin
public class EduLoginController {


    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "Tminto").data("avatar", "https://pics4.baidu.com/feed/14ce36d3d539b600cc55312f02bc5a23c45cb781.jpeg?token=3a48e51d4d64cda2f621e03e6fcaadbd");
    }
}
