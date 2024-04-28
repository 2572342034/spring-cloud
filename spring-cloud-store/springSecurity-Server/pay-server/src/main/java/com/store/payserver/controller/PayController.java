package com.store.payserver.controller;



import com.store.apiserver.model.po.OrderDetail;
import com.store.commen.utils.ResponseResult;
import com.store.payserver.service.IPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "支付订单管理接口")
@RestController
@RequestMapping("pay-orders")
@RequiredArgsConstructor
public class PayController {
    @Autowired
    private IPayOrderService iPayOrderService;
    @ApiOperation("添加订单到支付订单接口")
    @PostMapping("pay")
    ResponseResult addPay(@RequestBody List<OrderDetail> orderDetailItem){
        return iPayOrderService.addPay(orderDetailItem);
    }

    @PostMapping("changeOrderStatus")
    ResponseResult changeOrderStatus(@RequestBody List<OrderDetail> orderDetailItem){
        return iPayOrderService.changeOrderStatus(orderDetailItem);
    }
    @ApiOperation("得到支付订单列表")
    @GetMapping("GetFinishOrder")
    ResponseResult GetFinishOrder(){
        return iPayOrderService.GetFinishOrder();
    }



    @GetMapping("changeMoney")
    ResponseResult changeMoney(@RequestParam(value = "payMoney",defaultValue = "0") Integer money){
        return iPayOrderService.changeMoney(money);
    };
}
