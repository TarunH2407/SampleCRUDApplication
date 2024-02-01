package com.example.crudapp.sqs;


import com.example.crudapp.model.Item;
import com.example.crudapp.model.Order;
import com.example.crudapp.service.ItemService;
import com.example.crudapp.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SqsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SqsConsumer.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @SqsListener("${aws.sqs.queueName}")
    public void receiveMessage(@Payload Order newOrder) {
        logger.info("Order Placed: Success");
        Item newItem = itemService.findItemById(newOrder.getId()).orElse(null);
        if (newItem != null) {
            Item tempItem = new Item(
                    newItem.getId(),
                    newItem.getName(),
                    newItem.getQty() - 1,
                    newItem.getType()
            );
            itemService.saveItem(tempItem);
            newOrder.setStatus("SUCCESS");
            orderService.saveOrder(newOrder);
        }

    }
}


