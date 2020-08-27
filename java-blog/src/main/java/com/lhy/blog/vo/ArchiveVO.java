package com.lhy.blog.vo;

import com.lhy.blog.entity.Article;
import lombok.Data;

import java.util.List;

@Data
public class ArchiveVO {

    private String yearAndMonth;

    private List<Article> articles;

}
