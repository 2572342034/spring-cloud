package com.store.orderserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.store.commen.utils.ResponseResult;
import com.store.orderserver.model.po.OrderDetail;

import java.util.List;


/**
 * <p>
 * 订单详情表 服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface IOrderDetailService extends IService<OrderDetail> {
    ResponseResult ShowOrderDetail();
    ResponseResult OrderDeletById(Long id);
    OrderDetail GetOrderDetailById(String id);
    List<OrderDetail> GetOrderDetailListById(String id);
}
