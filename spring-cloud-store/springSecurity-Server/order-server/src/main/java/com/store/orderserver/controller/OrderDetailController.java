package com.store.orderserver.controller;



import com.store.commen.utils.ResponseResult;
import com.store.orderserver.model.po.OrderDetail;
import com.store.orderserver.service.IOrderDetailService;
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
@Api(tags = "订单详情页管理接口")
@RestController
@RequestMapping("order")
@RequiredArgsConstructor

@Slf4j
public class OrderDetailController {
    @Autowired
    private IOrderDetailService iOrderDetailService;


    @ApiOperation("查询订单详情页列表")
    @GetMapping("ShowOrderDetail")
    ResponseResult ShowOrderDetail(){
        return iOrderDetailService.ShowOrderDetail();
    }
    @DeleteMapping("DeleteOrder")
    @ApiOperation("订单详情页删除订单")
    ResponseResult DeleteOderDetail(@RequestParam(value = "id") Long id){
        return iOrderDetailService.OrderDeletById(id);
    }

    @GetMapping("GetOrderDetailById")
    OrderDetail GetOrderDetailById(@RequestParam("id") String id){
        return iOrderDetailService.GetOrderDetailById(id);
    }
    @GetMapping("GetOrderDetailListById")
    List<OrderDetail> GetOrderDetailListById(@RequestParam("id") String id){
        return iOrderDetailService.GetOrderDetailListById(id);
    }
    @ApiOperation("更新订单详情页信息")
    @PostMapping("saveOrUpdateOrderDetail")
    void saveOrUpdateOrderDetail(@RequestBody OrderDetail orderDetail){
        iOrderDetailService.updateById(orderDetail);
    }

}
