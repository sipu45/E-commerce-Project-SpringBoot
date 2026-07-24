package com.EcommerceProject.Service;

import com.EcommerceProject.Model.Cart;
import com.EcommerceProject.Payload.CartDTO;
import com.EcommerceProject.Repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public CartDTO addProductToCart(Long productId, Integer quantity) {
        // Find existing or create one
        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        // Retrieve the Product Details
        // Perform Validation
        // Create Cart Item
        // Save Cart Item
        // Return updated Cart
        return null;
    }
}
