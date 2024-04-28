package com.store.orderserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.store.commen.utils.ResponseResult;
import com.store.orderserver.model.po.OrderLogistics;


import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface IOrderLogisticsService extends IService<OrderLogistics> {

    ResponseResult addOrderLogistics(List<OrderLogistics> orderLogistics);
    OrderLogistics GetOrderLogisticsById(String id);
}
