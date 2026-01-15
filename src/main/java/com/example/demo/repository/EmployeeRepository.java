package com.example.demo.repository;

import com.example.demo.entity.Employee;
import com.example.demo.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    default Employee getEmployeeOrThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new NotFoundException("Работник с id: " + id + " не найден"));
    }
}
