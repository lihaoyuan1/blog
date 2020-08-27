package com.lhy.blog.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.lhy.blog.entity.User;
import com.lhy.blog.service.LoginService;
import com.lhy.blog.service.RedisService;
import com.lhy.blog.service.TokenService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/admin/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisService redisService;

    @PostMapping("/")
    public Result<String> login(@RequestParam String username,
                                @RequestParam String password,
                                HttpServletResponse response) {
        if(!loginService.usernameExist(username))
            return new Result<>(false, "用户名不存在!");
        User user = loginService.check(username, password);
        if(user == null)
            return new Result<>(false, "用户名或密码错误!");
        redisService.set(user.getId(), JSONObject.toJSONString(user));
        Cookie cookie = new Cookie("token", tokenService.getToken(user.getId()));
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new Result<>(true, "登陆成功");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(HttpServletRequest request){
        String token = tokenService.getCookie(request);
        String userId = tokenService.decodeToken(token);
        User user = loginService.getUserInfo(userId);
        return new Result<>(true, "获取用户信息成功", user);
    }

    @GetMapping("/logout")
    public Result<?> logout(HttpServletRequest request,
                            HttpServletResponse response){
        String token = tokenService.getCookie(request);
        redisService.del(tokenService.decodeToken(token));
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new Result<>(true, "退出登陆成功!");
    }

    @GetMapping("/check")
    public Result<?> check(HttpServletRequest request){
        String token = tokenService.getCookie(request);
        if (token == null) {
            return new Result<>(false, "未登录!");
        }
        if(redisService.existKey(token)){
            return new Result<>(false, "登陆过期!");
        }
        return new Result<>(true, "已登录!");
    }
}
