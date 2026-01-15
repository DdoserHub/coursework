package com.example.demo.specification;

import com.example.demo.entity.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    public static Specification<Employee> filterBy(String name, String surname) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(builder.equal(root.get("name"), name));
            }

            if (surname != null) {
                predicates.add(builder.equal(root.get("surname"), surname));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
