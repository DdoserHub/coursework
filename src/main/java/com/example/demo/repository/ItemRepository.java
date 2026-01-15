package com.example.demo.repository;

import com.example.demo.entity.Item;
import com.example.demo.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    default Item getItemOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("Товар с id: " + id + " не найден"));
    }
}
