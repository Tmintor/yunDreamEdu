package com.tminto.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tminto.domain.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tminto.domain.vo.TeachQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author tminto
 * @since 2022-09-17
 */
public interface EduTeacherService extends IService<EduTeacher> {


    Page<EduTeacher> page(Integer current, Integer limit, TeachQuery teachQuery);
}
