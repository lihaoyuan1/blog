package com.lhy.blog.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    String getToken(String userId);

    String decodeToken(String token);

    String getCookie(HttpServletRequest request);
}
