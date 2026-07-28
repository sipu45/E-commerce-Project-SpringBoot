package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Cart;
import com.EcommerceProject.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = ?1 AND ci.product.id = ?2 ")
    CartItem findCartIemByProductIdAndCartId(Long cartId, Long productId);
}
