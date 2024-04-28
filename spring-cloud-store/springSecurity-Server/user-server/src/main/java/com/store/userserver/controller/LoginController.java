package com.store.userserver.controller;



import com.store.commen.model.dto.User;
import com.store.commen.utils.ResponseResult;
import com.store.userserver.model.vo.UserLoginFromVO;
import com.store.userserver.model.vo.UserLoginVO;
import com.store.userserver.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("user")
public class LoginController {
    @Autowired
    private LoginService loginService;
    @ApiOperation("用户登录接口")
    @PostMapping("login")
    public ResponseResult login(@RequestBody UserLoginFromVO user){
        return loginService.login(user);
    }
    @ApiOperation("用户退出登录接口")
    @GetMapping("logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
    @ApiOperation("根据ID查询用户接口")
    @GetMapping("selectUserById")
    User SelectUserById(@RequestParam("id") String id){
        return loginService.SelectUserById(id);
    }
    @ApiOperation("更新用户信息接口")
    @PostMapping("saveOrUpdateUser")
    void saveOrUpdateUser(@RequestBody User user){
        loginService.saveOrUpdate(user);
    }

//    @GetMapping("hello")
//    String hello(){
//        return "hello world";
//    }
}
