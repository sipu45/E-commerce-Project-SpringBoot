package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Cart;
import com.EcommerceProject.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query
    Cart findCartByEmail(String email);
}
