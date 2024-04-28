package com.store.orderserver.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.store.apiserver.client.ItemClient;
import com.store.apiserver.model.po.Item;
import com.store.commen.constants.MqConstants;
import com.store.commen.utils.ResponseResult;
import com.store.commen.utils.UserContext;

import com.store.orderserver.mapper.OrderDetailMapper;
import com.store.orderserver.mapper.OrderMapper;
import com.store.orderserver.model.po.Order;
import com.store.orderserver.model.po.OrderDetail;
import com.store.orderserver.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单详情表 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private final ItemClient itemClient;
//    private ItemMapper itemMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public ResponseResult ShowOrderDetail() {
        try {
//            LoginUser principal = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Long userid = principal.getUser().getId();
            Long userid = UserContext.getUser();
//            log.info("userid = " + userid);
            List<Order> orderList = orderMapper.selectList(new LambdaQueryWrapper<Order>().eq(Order::getUserId, userid));
//            log.info("orderList = " + orderList);
            ArrayList<OrderDetail> orderDetails = new ArrayList<>();
            for (Order order : orderList) {
                List<OrderDetail> list = IOrderDetailService.super.list(new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, order.getId()).eq(OrderDetail::getStatus,1));
//                log.info(" list =  " + list.toString());
                orderDetails.addAll(list);
            }
            return new ResponseResult(200,"Success",orderDetails);
        } catch (Exception e) {
            throw new RuntimeException("查询订单详情失败" + e);
        }
    }
    @Override
    public ResponseResult OrderDeletById(Long id) {
//        先找到要删除的订单
        OrderDetail orderDetail = getById(id);
//        根据订单的商品ID找到商品
//        Item item = itemMapper.selectById(orderDetail.getItemId());
        Item item = itemClient.queryItemById(orderDetail.getItemId());
        item.setStock(item.getStock() + orderDetail.getNum());
//        更新商品的数量,之前需要先删除订单 如果订单删除失败就不需要更新
        try {
//            orderDetailMapper.deleteById(id);
            removeById(id);
        } catch (Exception e) {
            throw new RuntimeException("订单删除失败",e);
        }
//        更新商品数量
//        itemMapper.updateById(item);
        itemClient.saveOrUpdateItem(item);
        rabbitTemplate.convertAndSend(MqConstants.STORE_EXCHANGE,MqConstants.STORE_INSERT_KEY,item.getId());
        return new ResponseResult(200,"Success");
    }

    @Override
    public OrderDetail GetOrderDetailById(String id) {
        return getById(id);
    }

    @Override
    public List<OrderDetail> GetOrderDetailListById(String id) {
        return orderDetailMapper.selectList(new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId,id));
    }


}
