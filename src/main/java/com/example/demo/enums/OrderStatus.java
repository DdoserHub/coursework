package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    NEW("Новый"),
    PROCESSING("В процессе"),
    COMPLETED("Завершен"),
    CANCELED("Отменен");

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }

    @JsonValue
    public String getTitle() {
        return title;
    }
}
