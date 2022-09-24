package com.tminto.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tminto.domain.EduSubject;
import com.tminto.domain.vo.CourseInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<Map<String, Object>> getAllSubject();

}
