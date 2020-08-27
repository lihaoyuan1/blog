package com.lhy.blog.service;

import com.lhy.blog.entity.User;
import com.lhy.blog.vo.UserVO;

import java.util.Map;

public interface LoginService {

    boolean usernameExist(String username);

    User check(String username, String password);

    User getUserInfo(String userId);

    Map<String, Object> getUserInfo();

}
