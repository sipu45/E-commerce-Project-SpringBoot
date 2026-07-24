package com.EcommerceProject.Service;

import com.EcommerceProject.Payload.CartDTO;

public interface CartService {
   CartDTO addProductToCart(Long productId, Integer quantity);
}
