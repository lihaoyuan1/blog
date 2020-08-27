package com.lhy.blog.interceptor;

import com.lhy.blog.service.RedisService;
import com.lhy.blog.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = tokenService.getCookie(request);
        if (token == null) {
            response.sendError(401, "未登录，请返回登陆!");
            return false;
        }
        String key = tokenService.decodeToken(token);
        if(key == null || redisService.existKey(key)){
            response.sendError(401, "登陆过期，请重新登陆!");
            return false;
        }
        redisService.refreshExpires(key);
        return true;
    }
}
