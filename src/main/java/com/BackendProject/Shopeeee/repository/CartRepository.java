package com.BackendProject.Shopeeee.repository;

import com.BackendProject.Shopeeee.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUserId(Long userId);
}
