package com.lhy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.lhy.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        if(existKey(key)){
            redisTemplate.opsForValue().set(key, value);
        }
        redisTemplate.expire(key, 3600, TimeUnit.SECONDS);  // 设置1小时过期
    }

    @Override
    public void refreshExpires(String key) {
        redisTemplate.expire(key, 3600, TimeUnit.SECONDS);
    }

    @Override
    public boolean existKey(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return value == null;
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }
}
