package com.BackendProject.Shopeeee.request;

import com.BackendProject.Shopeeee.model.Category;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private  int inventory;
    private String description;
    private Category category;
}
