package com.example.demo.repository;

import com.example.demo.entity.Client;
import com.example.demo.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    default Client getClientOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("Клиент с id: " + id + " не найден"));
    }
}
