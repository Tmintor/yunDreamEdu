package com.tminto.controller;

import com.tminto.common.R;
import com.tminto.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 吴员外
 * @date 2022/10/5 13:42
 */
@RestController
@RequestMapping("/edu/ssm")
public class SmsController {

    @Autowired
    private SmsService smsService;


    @GetMapping("/code/{phone}")
    public R getVerificationCode(@PathVariable String phone) {

        boolean flag = smsService.getVerificationCode(phone);
        return flag ? R.ok() : R.error().message("短信发送失败，请稍后重试");
    }

}
