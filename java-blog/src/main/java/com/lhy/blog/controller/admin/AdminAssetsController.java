package com.lhy.blog.controller.admin;

import com.alibaba.fastjson.JSONException;
import com.lhy.blog.mapper.ArticleMapper;
import com.lhy.blog.oss.OSSUtils;
import com.lhy.blog.service.ArticleService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/admin/oss")
public class AdminAssetsController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/upload")
    public Result<String> upload(@RequestParam MultipartFile file) throws IOException, JSONException {
        if (file.isEmpty())
            return new Result<>(false, "上传内容为空!");
        int idx = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf('.');
        String type = file.getOriginalFilename().substring(idx);
        String fileName = System.currentTimeMillis() + type;
        String objectName = "blog/" + fileName;
        OSSUtils.upload(file, objectName);
        String url = "https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/" + objectName;
        return new Result<>(true, "上传成功!", url);
    }

    /**上传文章内包含的图片
     * @return
     */
    @PostMapping("/base64")
    public Result<String> upload1(@RequestParam String base64) {
        String type = base64.substring(11, base64.indexOf(";"));
        base64 = base64.substring(base64.indexOf(',') + 1);
        String objectName = "assets/" + System.currentTimeMillis() + "." + type;
        OSSUtils.uploadBase64(objectName, base64);
        String url = "https://blog-lhy.oss-cn-hangzhou.aliyuncs.com/" + objectName;
        return new Result<>(true, "上传成功!", url);
    }

    @Deprecated
    @GetMapping("/uploadByBatch")
    public void uploadByBatch() throws IOException {
        articleService.uploadByBatch();
    }

}
