package com.store.orderserver.controller;



import com.store.apiserver.model.po.Cart;
import com.store.apiserver.model.po.Item;
import com.store.commen.utils.ResponseResult;
import com.store.orderserver.model.po.Order;
import com.store.orderserver.model.po.OrderDetail;
import com.store.orderserver.model.po.OrderLogistics;
import com.store.orderserver.service.IOrderDetailService;
import com.store.orderserver.service.IOrderLogisticsService;
import com.store.orderserver.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 订单业务
**/
@Api(tags = "订单管理接口")
@RestController
@RequestMapping("order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    @Autowired
    private IOrderService iOrderService;
    @ApiOperation("添加订单到订单列表")
    @PostMapping("addOrder")
    ResponseResult addOrder(@RequestBody List<Cart> CartItem){
//        log.info("CartItem" + CartItem.toString());
        return iOrderService.addOrder(CartItem);
    }
    @ApiOperation("根据订单id查询订单")
    @GetMapping("GetOrderById")
    Order GetOrderById(@RequestParam("id") String id){
        return iOrderService.GetOrderById(id);
    }
    @ApiOperation("更具用户id查询用户订单列表")
    @GetMapping("GetOrderList")
    List<Order> GetOrderList(@RequestParam("userid") Long userid,
                             @RequestParam("status") Integer status
                             ){
        return iOrderService.GetOrderList(userid,status);
    }
    @ApiOperation("更新订单信息")
    @PostMapping("saveOrUpdateOrder")
    void saveOrUpdateOrder(@RequestBody Order order){
        iOrderService.updateById(order);
    }
    @GetMapping("GetBalance")
    @ApiOperation("获取用户的余额")
    ResponseResult GetBalance(){
        return iOrderService.GetBalance();
    }
}
