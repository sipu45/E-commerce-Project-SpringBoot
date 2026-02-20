package com.EcommerceProject.Service;

import com.EcommerceProject.Exceptions.ResourceNotFoundException;
import com.EcommerceProject.Model.Category;
import com.EcommerceProject.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
//private final List<Category> categories = new ArrayList<>();

//    private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
//        System.out.println(category.getCategoryID());
//        category.setCategoryID(nextId++);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryID",categoryId));
        categoryRepository.delete(category);
        return "Category with categoryId " + categoryId + " Deleted Successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();

        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryID",categoryId));
        category.setCategoryID(categoryId);
        savedCategory = categoryRepository.save(category);
        return  savedCategory;



    }
}
