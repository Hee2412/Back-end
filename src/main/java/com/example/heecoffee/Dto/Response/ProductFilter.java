package com.example.heecoffee.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilter {
    private String productName;
    private String typeName;
    private Integer typeId;
    private Integer pageNumber = 0;
    private Integer pageSize = 12;
}
