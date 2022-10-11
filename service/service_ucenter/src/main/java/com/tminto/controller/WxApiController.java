package com.tminto.controller;

import com.google.gson.Gson;
import com.tminto.constant.WeiXinConstant;
import com.tminto.domain.UcenterMember;
import com.tminto.service.UcenterMemberService;
import com.tminto.service.WxApiService;
import com.tminto.util.HttpClientUtils;
import com.tminto.utils.HttpUtils;
import com.tminto.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Autowired
    private WxApiService wxApiService;


    //2.获取扫描人信息，添加数据
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session){
        String url = wxApiService.callback(code,state,session);
        if (url != null) {
            //返回前台首页面
            return "redirect:" + url;
        }
        return null;
    }

    //1.生成微信二维码
    @GetMapping("/login")
    public String getWxCode(){
        String url = wxApiService.getRedirectURL();
        //请求微信地址,重定向的方式
        return "redirect:" + url;
    }
}
