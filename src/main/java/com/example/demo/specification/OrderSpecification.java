package com.example.demo.specification;

import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {
    public static Specification<Order> filterBy(OrderStatus status, Instant createdAt, Long itemId) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(builder.equal(root.get("status"), status));
            }

            if (createdAt != null) {
                predicates.add(builder.equal(root.get("createdAt"), createdAt));
            }

            if (itemId != null) {
                Join<Order, Item> itemsJoin = root.join("items");
                predicates.add(builder.equal(itemsJoin.get("id"), itemId));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
