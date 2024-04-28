package com.store.userserver.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.store.commen.model.dto.User;
import com.store.commen.utils.ResponseResult;


public interface RegisterService extends IService<User> {

    ResponseResult register(User registerUser);
}
