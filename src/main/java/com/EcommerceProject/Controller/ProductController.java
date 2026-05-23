package com.EcommerceProject.Controller;

import com.EcommerceProject.Model.Product;
import com.EcommerceProject.Payload.ProductDTO;
import com.EcommerceProject.Payload.ProductResponse;
import com.EcommerceProject.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId) {

        ProductDTO savedProduct = productService.addProduct(categoryId,productDTO);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }


    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId){
        ProductResponse productResponse = productService.searchByCategory(categoryId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);

    }


    @GetMapping("/public/products/Keyword/{Keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String Keyword){
        ProductResponse productResponse = productService.searchByKeyword(Keyword);
        return new ResponseEntity<>(productResponse,HttpStatus.FOUND);

    }


}
