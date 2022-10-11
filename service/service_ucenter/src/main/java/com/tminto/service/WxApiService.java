package com.tminto.service;

import javax.servlet.http.HttpSession;

/**
 * @author 吴员外
 * @date 2022/10/9 20:32
 */
public interface WxApiService {

    String getRedirectURL();

    String callback(String code, String state, HttpSession session);

}
