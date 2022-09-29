package com.tminto.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tminto.domain.EduChapter;
import com.tminto.domain.EduVideo;
import com.tminto.mapper.EduChapterMapper;
import com.tminto.mapper.EduVideoMapper;
import com.tminto.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tminto
 * @since 2022-09-23
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoMapper eduVideoMapper;

    @Override
    public List<Map<String, Object>> getAllChapterVideoByCourseId(String courseId) {

        //查询所有Chapter
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(eduChapterQueryWrapper);

        //查询所有章节
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoMapper.selectList(eduVideoQueryWrapper);

        //包装章节和小节
        List<Map<String, Object>> allChapterVideo = new ArrayList<>();
        eduChapters.forEach(c -> {
            Map<String, Object> chapterMap = new HashMap<>();
            chapterMap.put("id", c.getId());
            chapterMap.put("title", c.getTitle());

            List<Map<String, Object>> VideoList = new ArrayList<>();
            eduVideos.forEach(e -> {
                if (e.getChapterId().equals(c.getId())) {
                    Map<String, Object> videoMap = new HashMap<>();
                    videoMap.put("id", e.getId());
                    videoMap.put("title", e.getTitle());
                    VideoList.add(videoMap);
                }
            });
            chapterMap.put("children", VideoList);
            allChapterVideo.add(chapterMap);
        });

        return allChapterVideo;
    }

    @Override
    public void deleteById(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        Integer count = eduVideoMapper.selectCount(wrapper);
        if (count > 0) {
            throw new RuntimeException("该章节含有小节，不能进行删除");
        } else {
            baseMapper.deleteById(chapterId);
        }
    }
}
