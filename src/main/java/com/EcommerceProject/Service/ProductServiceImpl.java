package com.EcommerceProject.Service;

import com.EcommerceProject.Exceptions.ResourceNotFoundException;
import com.EcommerceProject.Model.Category;
import com.EcommerceProject.Model.Product;
import com.EcommerceProject.Payload.ProductDTO;
import com.EcommerceProject.Repositories.CategoryRepository;
import com.EcommerceProject.Repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(
                        ()-> new ResourceNotFoundException("Category","categoryId",categoryId)
                );
        product.setImage("Default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice()-
                ((product.getDiscount() * 0.01) * product.getDiscount());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);



    }
}
