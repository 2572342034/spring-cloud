package com.store.addressserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.store.addressserver.model.po.Address;
import com.store.commen.utils.ResponseResult;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface IAddressService extends IService<Address> {
    ResponseResult getAddress();
    ResponseResult addAddress(Address address);
    ResponseResult changeAddress(Address address);
    ResponseResult deleteAddress(Address address);
}
