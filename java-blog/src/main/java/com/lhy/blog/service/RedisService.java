package com.lhy.blog.service;

public interface RedisService {

    void set(String key, String value);

    void refreshExpires(String key);

    boolean existKey(String key);

    String get(String key);

    void del(String key);
}
