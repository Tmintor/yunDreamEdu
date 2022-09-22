package com.tminto.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 吴员外
 * @date 2022/9/21 20:33
 */
public interface OssService {

    String fileUpLoad(MultipartFile file);

}
