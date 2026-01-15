package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EmployeeRole {
    MANAGER("Менеджер"),
    ADMINISTRATOR("Администратор");

    private final String title;

    EmployeeRole(String title) {
        this.title = title;
    }

    @JsonValue
    public String getTitle() {
        return title;
    }
}
