package com.example.crudapp.controller;

import com.example.crudapp.exception.AuthCustomException;
import com.example.crudapp.service.ItemService;
import com.example.crudapp.model.Item;
import com.example.crudapp.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthValidator authValidator;

    @GetMapping("/getAllItems")
    public ResponseEntity<List<Item>> getAllItems(@RequestHeader("Auth") String authToken) {

        try{
            authValidator.validate(authToken);
            List<Item> itemList = itemService.findAllItems();
            if (itemList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (AuthCustomException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/getItem/{type}")
    public ResponseEntity<List<Item>> getItemByType(@PathVariable String type, @RequestHeader("Auth") String authToken) {

        try{
            authValidator.validate(authToken);
            List<Item> itemList = itemService.findItemByType(type);
            if (itemList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (AuthCustomException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> addItem(@RequestBody Item item, @RequestHeader("Auth") String authToken) {

        try{
            authValidator.validate(authToken);
            Item newItem = itemService.saveItem(item);
            return new ResponseEntity<>(newItem, HttpStatus.CREATED);
        } catch (AuthCustomException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable Long id, @RequestHeader("Auth") String authToken) {

        try{
            authValidator.validate(authToken);
            itemService.deleteItemById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AuthCustomException ae) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
