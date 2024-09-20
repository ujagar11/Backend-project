package com.BackendProject.Shopeeee.service.cart;

import com.BackendProject.Shopeeee.model.CartItem;

public interface ICartItemService {

    void addItemToCart(Long cartId,Long productId,int quantity);

    void removeItemFromCart(Long cartId, Long productId);

    void updateItemQuantity(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
