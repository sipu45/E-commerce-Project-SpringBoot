package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.email=?1 ")
    Cart findCartByEmail(String email);




}
