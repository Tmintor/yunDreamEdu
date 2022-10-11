package com.tminto.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.tminto.common.R;
import com.tminto.service.VodService;
import com.tminto.util.InitVideoClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 吴员外
 * @date 2022/9/27 22:45
 */
@Service
public class VodServiceImpl implements VodService {

    @Value("${aliyun.oss.file.keyid}")
    private String accessKeyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String accessKeySecret;


    @Override
    public String upLoadVideo(MultipartFile file) {
        //title上传之后显示名称
        //fileName原始名称
        try {
            String fileName = file.getOriginalFilename();
            String title = file.getOriginalFilename().substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            videoId = response.getVideoId();
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void deleteVideo(String id) {
        try {
            DefaultAcsClient client = InitVideoClientUtil.initVodClient(accessKeyId,accessKeySecret);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("删除视频失败");
        }
    }

    @Override
    public String getPlayAuth(String vid) {
        GetVideoPlayAuthRequest request = null;
        GetVideoPlayAuthResponse response = null;
        try {
            DefaultAcsClient client = InitVideoClientUtil.initVodClient(accessKeyId, accessKeySecret);
            request = new GetVideoPlayAuthRequest();
            //向request对象中设置视频id
            request.setVideoId(vid);

            //调用方法获得凭证
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new RuntimeException("获取视频凭证失败");
        }
        return response.getPlayAuth();
    }


}
