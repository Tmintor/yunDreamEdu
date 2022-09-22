package com.tminto.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tminto.domain.EduSubject;
import org.springframework.web.multipart.MultipartFile;


public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

}
