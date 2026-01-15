package com.example.demo.dto.OrderDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderAddItemsRequest {
    private List<Long> itemIds;
}
