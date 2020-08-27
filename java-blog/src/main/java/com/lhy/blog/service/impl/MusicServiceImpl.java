package com.lhy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.blog.entity.Music;
import com.lhy.blog.mapper.MusicMapper;
import com.lhy.blog.service.MusicService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public List<Music> getMusicList() {
        return musicMapper.selectList(new QueryWrapper<>());
    }
}
