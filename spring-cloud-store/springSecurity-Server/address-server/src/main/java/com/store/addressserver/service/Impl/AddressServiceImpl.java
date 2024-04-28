package com.store.addressserver.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.store.addressserver.mapper.AddressMapper;
import com.store.addressserver.model.po.Address;
import com.store.addressserver.service.IAddressService;

import com.store.commen.utils.ResponseResult;
import com.store.commen.utils.UserContext;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
@Service
@Slf4j
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

    @Override
    public ResponseResult getAddress() {
        try {
            //获取用户ID
//            LoginUser principal = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Long userId = principal.getUser().getId();
            Long userId = UserContext.getUser();
            List<Address> addresses = this.list(new LambdaQueryWrapper<Address>()
                    .eq(Address::getUserId, userId));
            return new ResponseResult(200,"Success",addresses);
        } catch (Exception e) {
            throw new RuntimeException("查询地址信息异常", e);
        }

    }

    @Override
    public ResponseResult addAddress(Address address) {
        try {
            //获取用户ID
//            LoginUser principal = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Long userId = principal.getUser().getId();
            Long userId = UserContext.getUser();
            address.setUserId(userId);
            Address address1 = this.getBaseMapper().selectOne(new LambdaQueryWrapper<Address>().eq(Address::getIsDefault, 1));
            //如果该用户的地址有至少一个(就是已经有默认的地址了)
            if (address1 != null){
                address1.setIsDefault(0);
                try {
                    this.getBaseMapper().updateById(address1);
                }catch (Exception e){
                    throw new RuntimeException("更新旧的数据失败");
                }
                this.getBaseMapper().insert(address);
            }
            //如果一个也没有（新用户）
            else {
                this.getBaseMapper().insert(address);
            }
            return new ResponseResult(200,"Success");
        } catch (RuntimeException e) {
            throw new RuntimeException("地址新增失败");
        }

    }

    @Override
    public ResponseResult changeAddress(Address address) {
        try {
            //获取用户ID
//            LoginUser principal = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            Long userId = principal.getUser().getId();
            Long userId = UserContext.getUser();
            address.setUserId(userId);
            Address address1 = this.getBaseMapper().selectOne(new LambdaQueryWrapper<Address>().eq(Address::getIsDefault, 1));
            address1.setIsDefault(0);
            try {
                this.getBaseMapper().updateById(address1);
            }catch (Exception e){
                throw new RuntimeException("更新旧的数据失败");
            }
            this.getBaseMapper().updateById(address);
            return new ResponseResult(200,"Success");
        } catch (RuntimeException e) {
            throw new RuntimeException("更新添加的地址失败");
        }
    }

    @Override
    public ResponseResult deleteAddress(Address address) {
        try {
            // 正在删除默认的地址
            if (address.getIsDefault() == 1) {

                // 查询所有非默认地址
                List<Address> nonDefaultAddresses = this.getBaseMapper().selectList(
                        new LambdaQueryWrapper<Address>().eq(Address::getIsDefault, 0)
                );

                // 如果存在非默认地址
                if (!nonDefaultAddresses.isEmpty()) {
                    // 选择第一个非默认地址，并将其设为默认地址
                    Address newDefaultAddress = nonDefaultAddresses.get(0);
                    newDefaultAddress.setIsDefault(1);
                    this.getBaseMapper().updateById(newDefaultAddress);
                    // 然后删除原默认地址
                    this.getBaseMapper().deleteById(address);
                }
                // 若不存在非默认地址（即只剩默认地址）
                else {
                    this.getBaseMapper().deleteById(address);
                }
            }
            else {
                this.getBaseMapper().deleteById(address);
            }
            return new ResponseResult(200,"Success");
        } catch (Exception e) {
            throw new RuntimeException("地址删除失败" ,e);
        }
    }


}
