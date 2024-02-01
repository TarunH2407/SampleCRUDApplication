package com.example.crudapp.service;

import com.example.crudapp.model.Item;
import com.example.crudapp.model.Order;
import com.example.crudapp.model.User;
import com.example.crudapp.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
//import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
//import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    public boolean isItemAvailable(Long itemId) {

        Optional<Item> optionalItem = itemService.findItemById(itemId);

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            return item.getQty() > 0;
        } else {
            return false;
        }
    }

    public boolean doesUserExist(Long userId) {
        Optional<User> optionalUser = userService.findUserById(userId);

        return optionalUser.isPresent();
    }
    public Order isPurchaseValid(Long itemId, Long userId) {

        Date date = new Date();
        Order newOrder = new Order();
        newOrder.setItemId(itemId);
        newOrder.setStatus("INIT");
        newOrder.setUserId(userId);
        newOrder.setDate(date);


        if(!isItemAvailable(itemId) || !doesUserExist(userId))
        {
            newOrder.setStatus("FAILURE");
            return newOrder;
        }
        else {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startOfDay = now.with(LocalTime.MIN);
            LocalDateTime endOfDay = now.with(LocalTime.MAX);
            Date todayStart = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
            Date todayEnd = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

            List<Order> userOrdersForItem = orderRepository.findByItemIdAndUserIdAndDateBetween(itemId, userId, todayStart, todayEnd);
            if(userOrdersForItem.size() < 2)
            {
                return newOrder;
            }
            else
            {
                return newOrder;
            }
        }
    }


//    @SqsListener(value = "${aws.queueName}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
//    public void receiveMessage(Order order) {
//        // do something with the message
//        Item newItem = itemService.findItemById(order.getId()).orElse(null);
//        if(newItem != null)
//        {
//            Item tempItem = new Item(
//                    newItem.getId(),
//                    newItem.getName(),
//                    newItem.getQty() - 1,
//                    newItem.getType()
//            );
//            itemService.saveItem(tempItem);
//            order.setStatus("SUCCESS");
//            System.out.println("Order Successfull");
//        }
//        else {
//            order.setStatus("FAILURE");
//            System.out.println("Order Unsuccessfull");
//        }
//
//    }

}
