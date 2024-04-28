package com.store.itemserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.store.itemserver.model.po.Item;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ItemMapper extends BaseMapper<Item> {

}
