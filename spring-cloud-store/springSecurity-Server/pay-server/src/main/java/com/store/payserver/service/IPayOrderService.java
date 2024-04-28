package com.store.payserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.store.apiserver.model.po.OrderDetail;
import com.store.commen.utils.ResponseResult;
import com.store.payserver.model.po.PayOrder;


import java.util.List;

/**
 * <p>
 * 支付订单 服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-16
 */
public interface IPayOrderService extends IService<PayOrder> {
    ResponseResult addPay(List<OrderDetail> orderDetailItem);
    ResponseResult changeOrderStatus(List<OrderDetail> orderDetailItem);
    ResponseResult GetFinishOrder();
    ResponseResult changeMoney(Integer money);
}
