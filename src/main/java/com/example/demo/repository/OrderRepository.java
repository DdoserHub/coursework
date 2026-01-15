package com.example.demo.repository;

import com.example.demo.entity.Order;
import com.example.demo.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    default Order getOrderOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("Заказа с id: " + id + " не найден"));
    }
}
