package com.tminto;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

/**
 * @author 吴员外
 * @date 2022/9/27 22:08
 */
public class TestApplication {

    public static void main(String[] args) throws ClientException {
        getPlayUrl();
    }

    public static void getPlayUrl() throws ClientException {
        //根据视频ip获取视频播放地址
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tKzcuNvyr8HXizh7XkB","3l95gHC61TRFIXcYaHh6VJWPKXBLmR");

        //创建获取视频地址的request和response
        GetPlayInfoRequest request  = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象设置视频id
        request.setVideoId("c67ce1b83e1b4f689c6776f83cfe003e");

        //调用初始化对象里面的方法，传递request，获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

}
