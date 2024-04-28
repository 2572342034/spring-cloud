package com.store.orderserver.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.store.apiserver.client.CartClient;
import com.store.apiserver.client.ItemClient;
import com.store.apiserver.client.UserClient;
import com.store.apiserver.model.dto.User;
import com.store.apiserver.model.po.Cart;
import com.store.apiserver.model.po.Item;
import com.store.commen.constants.MqConstants;
import com.store.commen.utils.ResponseResult;
import com.store.commen.utils.SnowflakeIdGenerator;
import com.store.commen.utils.UserContext;
import com.store.orderserver.mapper.OrderDetailMapper;
import com.store.orderserver.mapper.OrderMapper;
import com.store.orderserver.model.po.Order;
import com.store.orderserver.model.po.OrderDetail;
import com.store.orderserver.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
//    private CartMapper cartMapper;
    private final CartClient cartClient;
    @Autowired
//    private ItemMapper itemMapper;
    private final ItemClient itemClient;
    @Autowired
//    private UserMapper userMapper;
    private final UserClient userClient;
    @Override
    public ResponseResult addOrder(List<Cart> CartItem) {
        try {
            Order order = new Order();
            OrderDetail orderDetail = new OrderDetail();
            // TODO 使用雪花算法生成用户 订单编号
            SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1); // 假设我们只有一个节点，设置工号为1
            long orderId = generator.nextId();
            for (Cart cartitem : CartItem) {
                // 获取Item里面的商品
//                Item item = itemMapper.selectById(cartitem);
                Item item = itemClient.queryItemById(cartitem.getItemId());
                // 库存减少
                item.setStock(item.getStock() - cartitem.getNum());
//                itemMapper.updateById(item);
                itemClient.saveOrUpdateItem(item);
                rabbitTemplate.convertAndSend(MqConstants.STORE_EXCHANGE,MqConstants.STORE_INSERT_KEY,item.getId().toString());
                BeanUtil.copyProperties(cartitem,order);
                order.setId(String.valueOf(orderId) );
                Integer totalFee = order.getTotalFee();
                if (totalFee == null){
                    totalFee =0;
                }
                order.setTotalFee(totalFee + cartitem.getPrice());
                // TODO 支付方式，现在默认为微信支付
                order.setPaymentType(3);
                // TODO 现在的状态，现在默认为未支付
                order.setStatus(1);
//                log.info("Order  = " + order.toString());
                saveOrUpdate(order,new QueryWrapper<Order>().eq("id",orderId));
                // TODO 同时也向OrderDetail存入数据
                BeanUtil.copyProperties(cartitem,orderDetail);
                orderDetail.setOrderId(String.valueOf(orderId));
                orderDetail.setId(null);
                orderDetail.setStatus(1);
                orderDetailMapper.insert(orderDetail);
//                cartMapper.deleteById(cartitem);
                cartClient.DeleteCartById(cartitem.getId());
            }
            return new ResponseResult(200,"Success");
        } catch (Exception e) {
            throw new RuntimeException("添加Order信息失败" +  e);
        }
    }

    @Override
    public ResponseResult GetBalance() {
//        LoginUser principal = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Long userid = principal.getUser().getId();
        Long userid = UserContext.getUser();
//        User user = userMapper.selectById(userid);
        User user = userClient.SelectUserById(userid.toString());
        return new ResponseResult(200,"Success",user.getBalance());
    }

    @Override
    public Order GetOrderById(String id) {
        return getById(id);
    }

    @Override
    public List<Order> GetOrderList(Long userid, Integer status) {
        return orderMapper.selectList(new LambdaQueryWrapper<Order>().eq(Order::getUserId,userid).eq(Order::getStatus,status));
    }


}
