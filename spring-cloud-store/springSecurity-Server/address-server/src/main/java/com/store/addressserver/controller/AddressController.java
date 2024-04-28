package com.store.addressserver.controller;

import com.store.addressserver.model.po.Address;
import com.store.addressserver.service.IAddressService;
import com.store.commen.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lfj
 */
@Api(tags = "收货地址管理接口")
@RestController
@RequestMapping("addresses")
@RequiredArgsConstructor
public class AddressController {
    @Autowired
    private IAddressService iAddressService;
    @ApiOperation("获取用户地址列表")
    @GetMapping("getAddress")
    ResponseResult getAddress(){
        return iAddressService.getAddress();
    };
    @ApiOperation("添加用户地址")
    @PostMapping("addAddress")
    ResponseResult addAddress(@RequestBody Address address){
        return iAddressService.addAddress(address);
    }
    @ApiOperation("更新用户地址")
    @PostMapping("changeAddress")
    ResponseResult changeAddress(@RequestBody Address address){
        return iAddressService.changeAddress(address);
    }
    @ApiOperation("删除用户地址")
    @PostMapping("deleteAddress")
    ResponseResult deleteAddress(@RequestBody Address address){
        return iAddressService.deleteAddress(address);
    }
}
