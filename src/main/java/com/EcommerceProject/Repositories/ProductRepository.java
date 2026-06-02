package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Category;
import com.EcommerceProject.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);

    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageDetails);
}
