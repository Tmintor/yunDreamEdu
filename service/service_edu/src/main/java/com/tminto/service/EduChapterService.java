package com.tminto.service;

import com.tminto.domain.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tminto
 * @since 2022-09-23
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Map<String, Object>> getAllChapterVideoByCourseId(String courseId);

    void deleteById(String chapterId);
}
