package com.lhy.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lhy.blog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void test(){
        System.out.println(UUID.randomUUID());
    }
}