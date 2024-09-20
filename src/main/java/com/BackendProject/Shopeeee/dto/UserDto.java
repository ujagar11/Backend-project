package com.BackendProject.Shopeeee.dto;

import com.BackendProject.Shopeeee.model.Cart;
import com.BackendProject.Shopeeee.model.Order;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String firstName;
    private String LastName;
    private String email;
    private List<OrderDto> orders;
    private CartDto cart;
}
