package com.example.crudapp.controller;

import com.example.crudapp.dto.OrderRequest;
import com.example.crudapp.exception.AuthCustomException;
import com.example.crudapp.model.Item;
import com.example.crudapp.model.Order;
import com.example.crudapp.model.User;
import com.example.crudapp.service.ItemService;
import com.example.crudapp.service.OrderService;
import com.example.crudapp.sqs.SqsProducer;
import com.example.crudapp.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SqsProducer producer;


    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("Auth") String authToken) {

        try{
            authValidator.validate(authToken);
            List<Order> orderList = orderService.findAllOrders();
            if (orderList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orderList, HttpStatus.OK);
        } catch (AuthCustomException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request, @RequestHeader("Auth") String authToken) {
        try {
            authValidator.validate(authToken);
            Order newOrder = orderService.isPurchaseValid(request.getItemId(), request.getUserId());
            orderService.saveOrder(newOrder);
            if(newOrder.getStatus().equals("INIT")) {
                producer.sendMessage(newOrder);
            }
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        } catch (AuthCustomException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
