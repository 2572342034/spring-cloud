package com.store.userserver.controller;


import com.store.commen.model.dto.User;
import com.store.commen.utils.ResponseResult;
import com.store.userserver.service.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户相关接口")
@RestController
@RequestMapping("user")

public class RegisterCotroller {
    @Autowired
    private RegisterService registerService;
    @ApiOperation("用户注册接口")
    @PostMapping("register")
    ResponseResult register(@RequestBody User registerUser) {
        return registerService.register(registerUser);
    }

}
