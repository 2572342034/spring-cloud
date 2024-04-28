package com.store.userserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.store.commen.model.dto.User;
import com.store.commen.utils.ResponseResult;
import com.store.userserver.model.vo.UserLoginFromVO;
import com.store.userserver.model.vo.UserLoginVO;
import org.springframework.web.server.ServerWebExchange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface LoginService extends IService<User> {
    ResponseResult login(UserLoginFromVO user);
    ResponseResult logout();

    User SelectUserById(String id);

}
