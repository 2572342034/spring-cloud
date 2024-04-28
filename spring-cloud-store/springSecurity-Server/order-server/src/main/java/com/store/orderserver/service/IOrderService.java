package com.store.orderserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.store.apiserver.model.po.Cart;
import com.store.commen.utils.ResponseResult;
import com.store.orderserver.model.po.Order;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface IOrderService extends IService<Order> {
    ResponseResult addOrder(List<Cart> CartItem);
    ResponseResult GetBalance();
    Order GetOrderById(String id);
    List<Order> GetOrderList(Long userid,Integer status);


}
