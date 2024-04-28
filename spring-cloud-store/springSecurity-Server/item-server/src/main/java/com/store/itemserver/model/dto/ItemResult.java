package com.store.itemserver.model.dto;

import lombok.Data;

import java.util.List;

@Data

public class ItemResult {
    private Long total;
    private List<ItemDoc> item_info;
    public ItemResult(){

    }
    public ItemResult(Long total,List<ItemDoc> item_info){
        this.total = total;
        this.item_info = item_info;
    }
}
