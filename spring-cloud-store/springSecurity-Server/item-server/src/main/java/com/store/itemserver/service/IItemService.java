package com.store.itemserver.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.store.commen.utils.ResponseResult;
import com.store.itemserver.model.po.Item;

import java.util.List;


/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface IItemService extends IService<Item> {
    ResponseResult Page(int size,int page,String key);
    ResponseResult suggestion(String prefix);
    ResponseResult GetType();
    Item queryItemByIds(Long ids);
    void saveOrUpdateItem(Item item);
    void insertById(Long id);

    void deleteById(Long id);
    void deteteItemById(Item item);


}
