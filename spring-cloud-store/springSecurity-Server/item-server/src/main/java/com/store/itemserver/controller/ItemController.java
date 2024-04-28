package com.store.itemserver.controller;

import com.store.commen.utils.ResponseResult;
import com.store.itemserver.model.po.Item;
import com.store.itemserver.service.IItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@Api(tags = "商品管理相关接口")
@RestController
@RequestMapping("item")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    @Autowired
    private IItemService itemService;
    @ApiOperation("分页查询接口")
    @GetMapping("GetItems")
    ResponseResult Page(@RequestParam(value = "size",defaultValue = "32") int size,
                        @RequestParam(value = "page",defaultValue = "1") int page,
                        @RequestParam(value = "key",defaultValue = "") String key
                        ){
        return itemService.Page(size,page,key);
    }
    @ApiOperation("搜索框提示词接口")
    @GetMapping("suggestion")
    ResponseResult Suggestion(@RequestParam(value = "prefix") String prefix){
        return itemService.suggestion(prefix);
    }
    //openFeign
    @ApiOperation("根据商品id查询商品接口")
    @GetMapping("GetById")
    Item queryItemById(@RequestParam("id") Long id){

        return itemService.queryItemByIds(id);
    };
    @ApiOperation("根据商品id更新商品接口")
    @PostMapping("saveOrUpdateItem")
    void saveOrUpdateItem(@RequestBody Item item){
        itemService.saveOrUpdateItem(item);
    }
    @ApiOperation("获取商品类型接口")
    @GetMapping("GetType")
    ResponseResult GetType(){
        return itemService.GetType();
    }
    @ApiOperation("根据商品id删除商品")
    @DeleteMapping("deteteItemById")
    void deteteItemById(@RequestBody Item item){
        itemService.deteteItemById(item);
    }

}
