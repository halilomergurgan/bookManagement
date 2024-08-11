package com.example.bookmanagement.controller;

import com.example.bookmanagement.entity.Category;
import com.example.bookmanagement.exception.CustomResponse;
import com.example.bookmanagement.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<CustomResponse<Category>> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        CustomResponse<Category> response = new CustomResponse<>(savedCategory, "Category created successfully", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CustomResponse<List<Category>>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        CustomResponse<List<Category>> response = new CustomResponse<>(categories, "Categories fetched successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<Category>> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    Category savedCategory = categoryRepository.save(category);
                    CustomResponse<Category> response = new CustomResponse<>(savedCategory, "Category updated successfully", HttpStatus.OK.value());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(new CustomResponse<>(null, "Category not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<Void>> deleteCategory(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(category -> {
                    categoryRepository.delete(category);
                    CustomResponse<Void> response = new CustomResponse<>(null, "Category deleted successfully", HttpStatus.NO_CONTENT.value());
                    return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(new CustomResponse<>(null, "Category not found", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND));
    }
}
