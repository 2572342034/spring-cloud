package com.store.userserver;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.store.commen.model.dto.User;

import com.store.commen.utils.RedisCache;
import com.store.userserver.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class UserServerApplicationTests {
@Autowired
private  UserMapper userMapper;
@Autowired
private RedisCache redisCache;
    @Test
    void contextLoads() {
    }
    @Test
    void getName(){
      ;
//        User user = userMapper.selectOne(wrapper);
//        List<User> user = userMapper.selectList(new LambdaQueryWrapper<User>().eq(User::getUserName,"liu"));
//        log.info("username" + user);
//        String redisKey = "login:" + 5;
//        LoginUser loginUser = redisCache.getCacheObject(redisKey);
//        log.info("loginUser = " + loginUser);
    }
    @Test
    void getMapper(){
        LambdaQueryWrapper<User> eq = new LambdaQueryWrapper<User>().eq(User::getId, 2);
        log.info("eq" + eq.toString());
    }
}
