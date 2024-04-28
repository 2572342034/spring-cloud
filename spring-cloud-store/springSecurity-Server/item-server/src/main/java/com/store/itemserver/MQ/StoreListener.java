package com.store.itemserver.MQ;


import com.store.commen.constants.MqConstants;
import com.store.itemserver.service.IItemService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreListener {
    @Autowired
    private IItemService itemService;
    @RabbitListener(queues = MqConstants.STORE_INSERT_QUEUE)
    public void listenStoresInsertOrUpdate(Long id){
        itemService.insertById(id);
    }
    @RabbitListener(queues = MqConstants.STORE_DELETE_QUEUE)
    public void listenHotelDelete(Long id){
        itemService.deleteById(id);
    }
}
