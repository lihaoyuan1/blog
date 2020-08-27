package com.lhy.blog.controller.admin;

import com.lhy.blog.entity.Tag;
import com.lhy.blog.service.TagService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/addTag")
    public Result<?> addTag(@RequestParam String tagName){
        Integer t = tagService.countByTagName(tagName);
        if (t > 0)
            return new Result<>(false, "已存在该标签!");
        Integer rows = tagService.createTag(tagName);
        if (rows < 1){
            return new Result<>(false, "创建标签失败，请稍后再试!");
        }
        return new Result<>(true, "创建标签成功!");
    }

    @GetMapping("/allTag")
    public Result<List<Tag>> allTag(){
        List<Tag> tagList = tagService.getAll();
        return new Result<>(true, "获取成功!", tagList);
    }

    @GetMapping("/allTagSimple")
    public Result<List<Tag>> allTagSimple(){
        List<Tag> tagList = tagService.getAllSimple();
        return new Result<>(true, "获取列表成功!", tagList);
    }

    @PostMapping("/updateTag")
    public Result<?> updateTag(Tag tag){
        Integer t = tagService.countByTagName(tag.getTagName());
        if (t > 0)
            return new Result<>(false, "已存在该标签!");
        Integer rows = tagService.updateTag(tag);
        if (rows < 1){
            return new Result<>(false, "修改标签失败，请稍后再试!");
        }
        return new Result<>(true, "修改标签成功!");
    }

    @PostMapping("/deleteTag")
    public Result<List<Tag>> deleteTag(@RequestParam Integer id){
        Integer rows = tagService.deleteTag(id);
        if (rows < 1){
            return new Result<>(false, "删除标签失败，请稍后再试!");
        }
        List<Tag> result = tagService.getAll();
        return new Result<>(true, "删除标签成功!", result);
    }

}
