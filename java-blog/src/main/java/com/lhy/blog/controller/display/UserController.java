package com.lhy.blog.controller.display;

import com.lhy.blog.service.LoginService;
import com.lhy.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/info")
    public Result<?> getInfo(){
        Map<String, Object> result = loginService.getUserInfo();
        return new Result<>(true, "获取成功", result);
    }

}
