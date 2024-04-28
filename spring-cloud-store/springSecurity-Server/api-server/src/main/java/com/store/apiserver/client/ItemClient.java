package com.store.apiserver.client;


import com.store.apiserver.config.DefaultFeignConfig;
import com.store.apiserver.model.po.Item;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "item-service",configuration = DefaultFeignConfig.class)
//@RequestMapping("item")
public interface ItemClient {
    @GetMapping("/item/GetById")
    Item queryItemById(@RequestParam("id") Long id);
    @PostMapping("/item/saveOrUpdateItem")
    void saveOrUpdateItem(Item item);
    @DeleteMapping("deteteItemById")
    void deteteItemById(@RequestBody Item item);

}
