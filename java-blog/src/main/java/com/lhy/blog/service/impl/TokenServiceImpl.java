package com.lhy.blog.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lhy.blog.service.TokenService;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String secret = "sdjhakdhajdklsl;o653632";

    @Override
    public String getToken(String userId) {
        return JWT.create().withAudience(userId).sign(Algorithm.HMAC256(secret));
    }

    @Override
    public String decodeToken(String token) {
        return JWT.decode(token).getAudience().get(0);
    }

    @Override
    public String getCookie(HttpServletRequest request) {
        String result = null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                result = cookie.getValue();
            }
        }
        return result;
    }

}
