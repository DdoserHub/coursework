package com.example.demo.specification;

import com.example.demo.entity.Client;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClientSpecification {

    public static Specification<Client> filterBy(String name, String surname, String email, String number) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(builder.equal(root.get("name"), name));
            }

            if (surname != null) {
                predicates.add(builder.equal(root.get("surname"), surname));
            }

            if (email != null) {
                predicates.add(builder.equal(root.get("email"), email));
            }

            if (number != null) {
                predicates.add(builder.equal(root.get("number"), number));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
