package com.store.cartserver.controller;



import com.store.cartserver.service.ICartService;
import com.store.commen.utils.ResponseResult;
import com.store.commen.utils.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "购物车相关接口")
@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    @Autowired
    private ICartService iCartService;

    @ApiOperation("添加商品到购物车")
    @GetMapping("addCarts")
    ResponseResult Carts(@RequestParam(value = "id") Long id,
                         @RequestParam(value = "num") int num
                         ){
        return iCartService.Cart(id,num);
    }
    @ApiOperation("查询购物车列表")
    @GetMapping("ShowCarts")
    ResponseResult ShowCarts(@RequestHeader(value = "user-info",required = false) String userInfo){
        return iCartService.ShowCarts();
    }
    @ApiOperation("删除购物车商品")
    @GetMapping("DeleteCart")
    ResponseResult DeleteCart(@RequestParam(value = "id") Long id){
        try {
            iCartService.removeById(id);
            return new ResponseResult(200,"Success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @ApiOperation("根据id删除购物车商品")
    @GetMapping("DeleteCartById")
    void DeleteCartById(@RequestParam(value = "id") Long id){
        try {
            iCartService.removeById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
