package com.tminto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.domain.EduTeacher;
import com.tminto.domain.vo.TeachQuery;
import com.tminto.mapper.EduTeacherMapper;
import com.tminto.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Wrapper;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author tminto
 * @since 2022-09-17
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    @Autowired
    private EduTeacherMapper eduTeacherMapper;

    @Override
    public Page<EduTeacher> page(Integer current, Integer limit, TeachQuery teachQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>();

        String name = teachQuery.getName();
        String begin = teachQuery.getBegin();
        String end = teachQuery.getEnd();
        Integer level = teachQuery.getLevel();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        wrapper.orderByDesc("gmt_create");
        eduTeacherMapper.selectPage(pageTeacher,wrapper);
        return pageTeacher;
    }
}
