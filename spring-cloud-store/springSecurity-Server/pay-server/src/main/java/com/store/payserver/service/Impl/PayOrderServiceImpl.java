package com.store.payserver.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.store.apiserver.client.OrderClient;
import com.store.apiserver.client.OrderDetailClient;
import com.store.apiserver.client.OrderLogisticsClient;
import com.store.apiserver.client.UserClient;
import com.store.apiserver.model.dto.User;
import com.store.apiserver.model.po.Order;
import com.store.apiserver.model.po.OrderDetail;
import com.store.apiserver.model.po.OrderLogistics;

import com.store.commen.utils.ResponseResult;
import com.store.commen.utils.SnowflakeIdGenerator;
import com.store.commen.utils.UserContext;
import com.store.payserver.mapper.PayOrderMapper;
import com.store.payserver.model.po.PayOrder;
import com.store.payserver.service.IPayOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 支付订单 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-16
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {
    @Autowired
//    private OrderMapper orderMapper;
    private final OrderClient orderClitent;
    @Autowired
//    private OrderDetailMapper orderDetailMapper;
    private final OrderDetailClient orderDetailClient;
    @Autowired
//    private OrderLogisticsMapper orderLogisticsMapper;
    private final OrderLogisticsClient orderLogisticsClient;
    @Autowired
//    private UserMapper userMapper;
    private final UserClient userClient;
    @Override
    public ResponseResult addPay(List<OrderDetail> orderDetailItem) {
        try {
//            LoginUser principal = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Long userId = principal.getUser().getId();
            Long userId = UserContext.getUser();
            PayOrder payOrder = new PayOrder();
            payOrder.setPayType(5);
            payOrder.setBizUserId(userId);
            payOrder.setCreateTime(LocalDateTime.now(ZoneId.systemDefault()));
            payOrder.setUpdateTime(payOrder.getCreateTime());
            payOrder.setPayOverTime(payOrder.getCreateTime().plusMinutes(30));
            SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1); // 假设我们只有一个节点，设置工号为1
            long payId = generator.nextId();
            payOrder.setPayOrderNo(String.valueOf(payId));
            payOrder.setPayChannelCode("balance");
            payOrder.setStatus(1);
            for (OrderDetail orderDetail : orderDetailItem) {
                // 设置订单编号和支付的订单编号绑定关系
                payOrder.setBizOrderNo(orderDetail.getOrderId());
                Integer amount = payOrder.getAmount();
                if (amount == null){
                    amount = 0;
                }
                payOrder.setAmount(amount + orderDetail.getPrice());
            }
            try {

                super.baseMapper.insert(payOrder);
            } catch (Exception e) {
                throw new RuntimeException("更新失败",e);
            }
            return new  ResponseResult(200,"Success");
        } catch (Exception e) {
            throw new RuntimeException("Pay数据新增失败",e);
        }
    }

    @Override
    public ResponseResult changeOrderStatus(List<OrderDetail> orderDetailItem) {
        //查找商品,可能有多个,并修改Order表中的status
        try {
            for (OrderDetail orderDetail : orderDetailItem) {
                Order order = orderClitent.GetOrderById(orderDetail.getOrderId());
                orderDetail.setStatus(2);
                order.setStatus(2);
//                orderMapper.updateById(order);
                orderClitent.saveOrUpdateOrder(order);
//                orderDetailMapper.updateById(orderDetail);
                orderDetailClient.saveOrUpdateOrderDetail(orderDetail);
            }
        } catch (Exception e) {
            throw new RuntimeException("更新Order表或OrderDetail的status失败",e);
        }

        return new ResponseResult(200,"Success");
    }

    @Override
    public ResponseResult GetFinishOrder() {
        //order表中status为2的就表示已经支付了的订单,同时注意要区分用户

        //获取用户ID
//        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = UserContext.getUser();
//        List<Order> orderList = orderMapper.selectList(new LambdaQueryWrapper<Order>().eq(Order::getUserId, loginUser.getUser().getId()).eq(Order::getStatus, 2));
        List<Order> orderList = orderClitent.GetOrderList(userId, 2);
        //存储已经支付了的订单详情
        List<HashMap<String,Object>> hashMapList = new ArrayList<>();
        try {
            for (Order order : orderList) {
                List<OrderDetail> orderDetailList = new ArrayList<>();
                List<OrderLogistics> orderLogisticsList = new ArrayList<>();
                List<PayOrder> payOrderId = new ArrayList<>();
                HashMap<String,Object> listListHashMap = new HashMap<>();
//                List<OrderDetail> orderDetails = orderDetailMapper.selectList(new LambdaQueryWrapper<OrderDetail>().eq(OrderDetail::getOrderId, order.getId()));
                List<OrderDetail> orderDetails = orderDetailClient.GetOrderDetailListById(order.getId());
                for (OrderDetail orderDetail : orderDetails) {
                    orderDetailList.add(orderDetail);
                }

//                orderLogisticsList.add(orderLogisticsMapper.selectById(order.getId()));
                orderLogisticsList.add(orderLogisticsClient.GetOrderLogisticsById(order.getId()));
                payOrderId.add(this.getBaseMapper().selectOne(new LambdaQueryWrapper<PayOrder>().eq(PayOrder::getBizOrderNo,order.getId())));
                listListHashMap.put("orderDetailList",orderDetailList);
                listListHashMap.put("orderLogisticsList", orderLogisticsList);
                listListHashMap.put("payOrderId",payOrderId);
                hashMapList.add(listListHashMap);

            }

        } catch (Exception e) {
            throw new RuntimeException("查询用户支付完成的订单或地址失败",e);
        }
        return new ResponseResult(200,"Success",hashMapList);
    }

    @Override
    public ResponseResult changeMoney(Integer money) {
        //获取用户
//        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long userid = loginUser.getUser().getId();
        Long userId = UserContext.getUser();

        try {
//            User user = userMapper.selectById(userid);
            User user = userClient.SelectUserById(userId.toString());
            user.setBalance(user.getBalance() - money);
//            userMapper.updateById(user);
            userClient.saveOrUpdateUser(user);
        } catch (Exception e) {
            throw new RuntimeException("更新用户余额错误！",e);
        }
        return new ResponseResult(200,"Success");
    }
}
