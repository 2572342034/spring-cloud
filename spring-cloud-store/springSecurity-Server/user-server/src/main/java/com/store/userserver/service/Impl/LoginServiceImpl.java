package com.store.userserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.store.commen.exception.BadRequestException;
import com.store.commen.exception.ForbiddenException;
import com.store.commen.model.dto.User;
import com.store.commen.utils.RedisCache;


import com.store.commen.utils.ResponseResult;
import com.store.commen.utils.UserContext;
import com.store.userserver.config.JwtProperties;
import com.store.commen.enums.UserStatus;
import com.store.userserver.mapper.UserMapper;
import com.store.userserver.model.vo.UserLoginFromVO;
import com.store.userserver.model.vo.UserLoginVO;
import com.store.userserver.service.LoginService;
import com.store.userserver.util.JwtTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




import java.util.Objects;



@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private final JwtTool jwtTool;
    @Autowired
    private final JwtProperties jwtProperties;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult login(UserLoginFromVO user) {
        //将前端传过来的用户信息放入Token中
        String username = user.getUserName();
        String password = user.getPassword();
        User userinfo = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUserName,username));
        if (Objects.isNull(userinfo)){
            throw new RuntimeException("用户名错误");
        }
        if (UserStatus.FROZEN == userinfo.getStatus()) {
            throw new ForbiddenException("用户被冻结");
        }
        // 4.校验密码
        if (!passwordEncoder.matches(password, userinfo.getPassword())) {
            throw new BadRequestException("用户名或密码错误");
        }
        //使用userid生成token
        Long userid = userinfo.getId();
        String jwt = jwtTool.createToken(userid,jwtProperties.getTokenTTL());
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserId(userid);
        userLoginVO.setBalance(userinfo.getBalance());
        userLoginVO.setToken(jwt);
        userLoginVO.setUsername(userinfo.getUserName());
        //authenicate存入redis
        redisCache.setCacheObject("login:" + userid,userLoginVO);
        return new ResponseResult(200,"Success",userLoginVO);
    }

    @Override
    public ResponseResult logout() {
        // 1.获取Request
//        从Redis中移除登录凭证
        redisCache.deleteObject("login:" + UserContext.getUser());

        return new ResponseResult(200,"退出成功");
    }

    @Override
    public User SelectUserById(String id) {
        return getById(id);
    }
}
