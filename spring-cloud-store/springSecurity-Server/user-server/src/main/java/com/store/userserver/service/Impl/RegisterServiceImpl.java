package com.store.userserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.store.commen.enums.UserStatus;
import com.store.commen.model.dto.User;
import com.store.commen.model.vo.User_Role;
import com.store.commen.utils.ResponseResult;
import com.store.userserver.mapper.UserMapper;
import com.store.userserver.mapper.User_RoleMapper;
import com.store.userserver.service.RegisterService;
import io.jsonwebtoken.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class RegisterServiceImpl extends ServiceImpl<UserMapper, User> implements RegisterService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private User_RoleMapper user_roleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User registerUser) {
        //注册的用户名为空时
        if (!Strings.hasText(registerUser.getUserName())) {
            throw new RuntimeException("用户名为空");
        }
        //用户名不为空,查询数据库是否有该用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, registerUser.getUserName());
        User user = userMapper.selectOne(wrapper);
        //在数据库中用户名已经存在
        if (!Objects.isNull(user)) {
            return new ResponseResult(500, "用户名已经存在，请重新登录");
        }
        //如果用户名在用户中不存在
        //修改性别
        if ("男".equals(registerUser.getSex())) {
            registerUser.setSex("0");
        } else if ("女".equals(registerUser.getSex())) {
            registerUser.setSex("1");
        } else {
            registerUser.setSex("2");
        }
        //        1.先加密密码 并存入对象中
        String encode = passwordEncoder.encode(registerUser.getPassword());
        registerUser.setPassword(encode);
        registerUser.setStatus(UserStatus.NORMAL);
        registerUser.setNickName("普通用户");
        registerUser.setCreateTime(LocalDateTime.now());
        //下面这个其实是每次登录或者登出的时间
        registerUser.setUpdateTime(LocalDateTime.now());
//        2.将数据插入数据表sys_user中
        userMapper.insert(registerUser);
//        3.再查询一次数据库,跟新用户和角色表
        LambdaQueryWrapper<User> wrapper2 = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, registerUser.getUserName());
        Long id = userMapper.selectOne(wrapper).getId();
        User_Role userRole = new User_Role();
        userRole.setUserId(id.toString());
        userRole.setRoleId("2");
//        插入
        user_roleMapper.insert(userRole);
        return new ResponseResult(200, "注册成功");

    }
}
