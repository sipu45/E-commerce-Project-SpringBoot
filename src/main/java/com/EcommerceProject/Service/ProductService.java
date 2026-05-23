package com.EcommerceProject.Service;

import com.EcommerceProject.Payload.ProductDTO;
import com.EcommerceProject.Payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);


    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    ProductResponse searchByKeyword(String keyword);
}
