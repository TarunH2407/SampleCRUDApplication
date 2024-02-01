package com.example.crudapp.service;

import com.example.crudapp.model.Item;
import com.example.crudapp.repo.ItemRepository;
import com.example.crudapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AuthService authService;

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> findItemByType(String type) {
        return itemRepository.findByType(type);
    }

    public Optional<Item> findItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItemById(Long id) {
        itemRepository.deleteById(id);
    }
}
