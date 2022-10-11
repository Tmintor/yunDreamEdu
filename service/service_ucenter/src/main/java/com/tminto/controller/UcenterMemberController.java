package com.tminto.controller;


import com.tminto.common.R;
import com.tminto.domain.RegisterVo;
import com.tminto.domain.UcenterMember;
import com.tminto.service.UcenterMemberService;
import com.tminto.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Tminto
 * @since 2022-10-05
 */
@RestController
@RequestMapping("/ucenter/member")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/login")
    public R login(@RequestBody UcenterMember userInfo) {
        String token = ucenterMemberService.login(userInfo);
        System.out.println(token);
        return R.ok().data("token", token);
    }

    @PostMapping("/register")
    public R register(@RequestBody RegisterVo formItem) {

        ucenterMemberService.register(formItem);
        return R.ok();
    }

    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        System.out.println(request.getHeader("token"));
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("memberInfo", member);
    }
}

