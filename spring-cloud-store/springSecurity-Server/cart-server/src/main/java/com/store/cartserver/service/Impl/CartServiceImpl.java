package com.store.cartserver.service.Impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.store.apiserver.client.ItemClient;
import com.store.apiserver.model.po.Item;
import com.store.cartserver.mapper.CartMapper;
import com.store.cartserver.model.po.Cart;
import com.store.cartserver.service.ICartService;

import com.store.commen.utils.ResponseResult;

import com.store.commen.utils.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {
    @Autowired
//    private IItemService itemService;
    private final ItemClient itemClient;

    @Override
    public ResponseResult Cart(Long id, int num) {
        try {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            LoginUser loginUser = (LoginUser) principal;
//            Long userId = loginUser.getUser().getId();
            Long userId = UserContext.getUser();
            //上面是获取用户ID的
            Item item = itemClient.queryItemById(id);
            Cart cart_item = ICartService.super.getById(id);
            //判断从数据库里面取的Cart是否为空，如果是空就new 一个对象
            cart_item = cart_item !=null?cart_item:new Cart();
            cart_item.setItemId(item.getId());
            cart_item.setUserId(userId);
            //复制Bean类，把item类复制到Cart_item
            BeanUtil.copyProperties(item, cart_item);
            if (Objects.isNull(cart_item.getNum())) {
                cart_item.setNum(num);
                cart_item.setPrice(cart_item.getNum() * item.getPrice());
                cart_item.setCreateTime(LocalDateTime.now());
                cart_item.setUpdateTime(cart_item.getCreateTime());
                ICartService.super.save(cart_item);
            }else {
                if (item.getStock() >= cart_item.getNum() + num){
                    cart_item.setNum(cart_item.getNum() + num);
                    cart_item.setPrice(cart_item.getPrice() * cart_item.getNum());
                    cart_item.setUpdateTime(LocalDateTime.now());
                    ICartService.super.updateById(cart_item);
                }
                else {
                    throw new RuntimeException("库存不足!!!");
                }
            }
            itemClient.saveOrUpdateItem(item);
            return new ResponseResult(200,"Success");
        } catch (Exception e) {
            throw new RuntimeException("查询Item失败!!" + e);
        }
    }




    @Override
    public ResponseResult ShowCarts() {
        try {
            //获取用户ID
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            LoginUser loginUser = (LoginUser) principal;
//            Long userId = loginUser.getUser().getId();
            Long userId = UserContext.getUser();
            LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Cart::getUserId,userId);

            return new ResponseResult(200,"Success",ICartService.super.list(wrapper));
        } catch (Exception e) {
            throw new RuntimeException("查询购物车商品信息失败" +  e);
        }
    }
}
