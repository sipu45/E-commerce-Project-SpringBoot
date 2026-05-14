package com.EcommerceProject.Service;

import com.EcommerceProject.Model.Product;
import com.EcommerceProject.Payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
