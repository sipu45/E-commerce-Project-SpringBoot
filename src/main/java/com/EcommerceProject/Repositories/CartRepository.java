package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Cart;
import com.EcommerceProject.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
