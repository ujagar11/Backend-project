package com.BackendProject.Shopeeee.repository;

import com.BackendProject.Shopeeee.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    void deleteAllByCartId(Long id);
}
