package com.store.cartserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.store.cartserver.model.po.Cart;
import com.store.commen.utils.ResponseResult;


/**
 * <p>
 * 订单详情表 服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface ICartService extends IService<Cart> {
    ResponseResult Cart(Long id, int num);
    ResponseResult ShowCarts();

}
