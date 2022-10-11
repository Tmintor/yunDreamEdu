package com.tminto.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 吴员外
 * @date 2022/9/27 22:44
 */

public interface VodService {
    String upLoadVideo(MultipartFile multipartFile);

    void deleteVideo(String id);

    String getPlayAuth(String vid);
}
