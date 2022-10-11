package com.tminto.service;

/**
 * @author 吴员外
 * @date 2022/10/5 13:56
 */
public interface SmsService {

    boolean getVerificationCode(String phone);

}
