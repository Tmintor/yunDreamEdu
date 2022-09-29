package com.tminto.mapper;

import com.tminto.domain.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tminto
 * @since 2022-09-23
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    Map<String, Object> selectAllCourseInfo(String id);
}
