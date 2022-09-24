package com.tminto.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tminto.domain.EduSubject;
import com.tminto.domain.excel.SubjectData;
import com.tminto.listener.SubjectExcelListener;
import com.tminto.mapper.EduSubjectMapper;
import com.tminto.service.EduSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-05
 */
@Transactional
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        //获取输入流
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Map<String, Object>> getAllSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);


        //查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);

        //封装
        List<Map<String, Object>> one = new ArrayList<>();
        oneSubjectList.forEach(s -> {
            Map<String, Object> oneSubject = new HashMap<>();
            oneSubject.put("id", s.getId());
            oneSubject.put("title", s.getTitle());
            List<Map<String, Object>> two = new ArrayList<>();
            twoSubjectList.forEach(t -> {
                if (t.getParentId().equals(s.getId())) {
                    Map<String, Object> twoSubject = new HashMap<>();
                    twoSubject.put("id", t.getId());
                    twoSubject.put("title", t.getTitle());
                    two.add(twoSubject);
                }
            });
            oneSubject.put("children", two);
            one.add(oneSubject);
        });

        return one;
    }

}
