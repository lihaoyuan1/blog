package com.lhy.blog.dto;

import lombok.Data;

@Data
public class QueryDto {

    private Integer year;

    private Integer month;

    private String type;

    private Integer categoryId;

    private String keyWords;

    private Integer sortType;

}
