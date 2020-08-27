package com.lhy.blog.controller.display;

import com.lhy.blog.entity.Music;
import com.lhy.blog.service.MusicService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/")
    public Result<List<Music>> music(){
        List<Music> musicList = musicService.getMusicList();
        return new Result<>(true, "获取成功!", musicList);
    }
}
