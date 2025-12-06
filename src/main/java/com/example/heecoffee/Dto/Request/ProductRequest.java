package com.example.heecoffee.Dto.Request;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequest {
    private String productName;
    private String description;
    private BigDecimal price;
    private String img;
    private List<Integer> typeIds; // Frontend gửi array of type IDs
}