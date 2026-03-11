package com.EcommerceProject.Controller;


import com.EcommerceProject.Config.AppConstants;
import com.EcommerceProject.Payload.CategoryDTO;
import com.EcommerceProject.Payload.CategoryResponse;
import com.EcommerceProject.Service.CategoryService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    // your choice to use constructor injection or field injection
//      public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }



    @GetMapping("/public/categories")
//    @RequestMapping(value ="/api/public/categories",method = RequestMethod.GET)  use ofRequestMapping
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false)Integer pageNumber ,
            @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE , required = false)Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY , required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR , required = false) String sortOrder ){
        /* List<Category> categories = categoryService.getAllCategories(); */
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize ,sortBy,sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);

    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO =categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO savedCategoryDTO = categoryService.deleteCategory(categoryId);
           return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);

    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable Long categoryId){
            CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<>(savedCategoryDTO,HttpStatus.OK);

    }


}
