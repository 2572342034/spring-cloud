package com.store.orderserver.controller;
import com.store.commen.utils.ResponseResult;
import com.store.orderserver.model.po.OrderLogistics;
import com.store.orderserver.service.IOrderLogisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * 订单业务
 **/

@RestController
@RequestMapping("order")
@RequiredArgsConstructor

@Slf4j
public class OrderLogisticsController {
    @Autowired
    private IOrderLogisticsService iOrderLogisticsService;


    @PostMapping("addOrderLogistics")
    ResponseResult addOrderLogistics(@RequestBody List<OrderLogistics> orderLogistics){
        return iOrderLogisticsService.addOrderLogistics(orderLogistics);
    }

    @GetMapping("GetOrderLogisticsById")
    OrderLogistics GetOrderLogisticsById(@RequestParam("id") String id){
        return iOrderLogisticsService.GetOrderLogisticsById(id);
    }

}
