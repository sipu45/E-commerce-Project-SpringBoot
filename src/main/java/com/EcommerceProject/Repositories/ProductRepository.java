package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Category;
import com.EcommerceProject.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}
