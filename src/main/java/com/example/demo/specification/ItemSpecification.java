package com.example.demo.specification;

import com.example.demo.entity.Item;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {
    public static Specification<Item> filterBy(String name, Integer cost) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(builder.equal(root.get("name"), name));
            }

            if (cost != null) {
                predicates.add(builder.equal(root.get("surname"), cost));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
