package com.BackendProject.Shopeeee.service.cart;

import com.BackendProject.Shopeeee.model.Cart;
import com.BackendProject.Shopeeee.model.User;

import java.math.BigDecimal;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
