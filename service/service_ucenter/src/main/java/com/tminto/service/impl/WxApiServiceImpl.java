package com.tminto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.tminto.constant.WeiXinConstant;
import com.tminto.domain.UcenterMember;
import com.tminto.mapper.UcenterMemberMapper;
import com.tminto.service.WxApiService;
import com.tminto.util.HttpClientUtils;
import com.tminto.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author 吴员外
 * @date 2022/10/9 20:34
 */
@Service
public class WxApiServiceImpl implements WxApiService {

    @Autowired
    private UcenterMemberMapper ucenterMemberMapper;

    @Override
    public String getRedirectURL() {
        // 微信开放平台授权baseUrl
        //? %s相当于占位符，可以填充参数
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //授权码需要传入加密过的URL,必须使用
        String redirectUrl = WeiXinConstant.REDIRECT_URL;//获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");//url编码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //设置%s参数值
        String url = String.format(
                baseUrl,
                WeiXinConstant.APP_ID,
                redirectUrl,
                "tminto");
        return url;
    }

    @Override
    public String callback(String code, String state, HttpSession session) {
        try {
            //1、得到授权临时票据code

            //2、拿着code请求微信的固定地址，得到两个值access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    WeiXinConstant.APP_ID,
                    WeiXinConstant.APP_SECRET,
                    code);
            //请求这个拼接好的地址，得到返回两个值access_token和openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println(accessTokenInfo);

            //使用gson转换工具Gson
            Gson gson = new Gson();
            HashMap<String,String> mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);

            String access_token = mapAccessToken.get("access_token");
            String openid = mapAccessToken.get("openid");


            //判断该微信信息是否注册过
            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.eq("openid", openid);
            UcenterMember member = ucenterMemberMapper.selectOne(wrapper);
            if (member == null) {
                //3\拿着access_token和openid，再去请求微信提供的固定地址，获取扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";

                //再次拼接微信地址
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);

                String userInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("userInfo" + userInfo);

                //获取的微信个人信息json信息进行转换
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");//昵称
                String headimgurl = (String) userInfoMap.get("headimgurl");//头像

                //把微信信息注册到数据库中
                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                ucenterMemberMapper.insert(member);
            }

            //使用jwt生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //返回首页面
            return "http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("登录失败");
        }

    }


}
