//package com.store.userserver.service.Impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//
//import com.store.commen.model.dto.User;
//import com.store.commen.model.vo.LoginUser;
//import com.store.userserver.mapper.AuthorityMenuMapper;
//import com.store.userserver.mapper.UserMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Objects;
//
///**
// * @Author 三更  B站： https://space.bilibili.com/663528522
// */
//@Service
//@Slf4j
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private AuthorityMenuMapper authorityMenuMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //根据用户名查询用户信息
////        log.info("userInfo = " + username);
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUserName,username);
//        User user = userMapper.selectOne(wrapper);
////        log.info("user = " + user);
//        if(Objects.isNull(user)){
//            throw new RuntimeException("用户名或密码错误");
//
//        }
//        //TODO 根据用户查询权限信息 添加到LoginUser中
//        List<String> permissionKeyList = authorityMenuMapper.selectPermsByUserId(user.getId());
////        List<String> list = new ArrayList<>(Arrays.asList("test"));
//        //封装成UserDetails对象返回
//        return new LoginUser(user,permissionKeyList);
//
//    }
//}