package com.EcommerceProject.Service;

import com.EcommerceProject.Model.Category;
import com.EcommerceProject.Payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);
}
