package com.tminto.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 吴员外
 * @date 2022/10/9 18:41
 */
@Component
@ConfigurationProperties(prefix = "wx.open")
public class WeiXinConstant {

    public static String APP_ID;

    public static String APP_SECRET;

    public static String REDIRECT_URL;

    public void setAppId(String appId) {
        WeiXinConstant.APP_ID = appId;
    }

    public void setAppSecret(String appSecret) {
        WeiXinConstant.APP_SECRET = appSecret;
    }

    public void setRedirectUrl(String redirectUrl) {
        WeiXinConstant.REDIRECT_URL = redirectUrl;
    }
}
