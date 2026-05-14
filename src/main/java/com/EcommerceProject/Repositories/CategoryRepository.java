package com.EcommerceProject.Repositories;

import com.EcommerceProject.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> { //(Type oF Entity, Type of primary key)
     Category findByCategoryName(String categoryName);
}
