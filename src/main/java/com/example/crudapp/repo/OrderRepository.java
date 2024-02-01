package com.example.crudapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crudapp.model.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByItemIdAndUserIdAndDateBetween(Long itemId, Long userId, Date startTime, Date endTime);
}
