package com.EcommerceProject.Service;

import com.EcommerceProject.Exceptions.ResourceNotFoundException;
import com.EcommerceProject.Model.Category;
import com.EcommerceProject.Model.Product;
import com.EcommerceProject.Payload.ProductDTO;
import com.EcommerceProject.Payload.ProductResponse;
import com.EcommerceProject.Repositories.CategoryRepository;
import com.EcommerceProject.Repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        Product product = modelMapper.map(productDTO,Product.class);
        product.setImage("Default.png");
        product.setCategory(category);
        double specialPrice = product.getPrice()-
                ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct,ProductDTO.class);



    }

    @Override
    public ProductResponse getAllProducts() {
       List<Product> products = productRepository.findAll();
       List<ProductDTO> productDTOS = products.stream()
               .map(product -> modelMapper.map(product,ProductDTO.class))
               .toList();

       ProductResponse productResponse = new ProductResponse();
       productResponse.setContent(productDTOS);
       return productResponse;

    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase( keyword);
//        List<Product> products = productRepository.findByProductNameIgnoreCase( '%'+keyword+'%');
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }
}
