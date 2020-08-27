package com.lhy.blog.controller.display;

import com.lhy.blog.entity.Tag;
import com.lhy.blog.service.TagService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/all")
    public Result<?> allTag(){
        List<Tag> result = tagService.getAll();
        return new Result<>(true, "获取成功", result);
    }

    @GetMapping("/getOne")
    public Result<?> getOne(@RequestParam Integer id){
        Tag result = tagService.getOne(id);
        return new Result<>(true, "获取成功", result);
    }

}
