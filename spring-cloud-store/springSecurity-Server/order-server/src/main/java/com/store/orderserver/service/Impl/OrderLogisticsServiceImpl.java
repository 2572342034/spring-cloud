package com.store.orderserver.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.store.commen.utils.ResponseResult;
import com.store.orderserver.mapper.OrderLogisticsMapper;
import com.store.orderserver.model.po.OrderLogistics;
import com.store.orderserver.service.IOrderLogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Service
public class OrderLogisticsServiceImpl extends ServiceImpl<OrderLogisticsMapper, OrderLogistics> implements IOrderLogisticsService {
    @Autowired
    private OrderLogisticsMapper orderLogisticsMapper;

    @Override
    public ResponseResult addOrderLogistics(List<OrderLogistics> orderLogistics) {
        this.save(orderLogistics.get(0));
        return new ResponseResult(200,"Success");
    }

    @Override
    public OrderLogistics GetOrderLogisticsById(String id) {
        return getById(id);
    }
}
