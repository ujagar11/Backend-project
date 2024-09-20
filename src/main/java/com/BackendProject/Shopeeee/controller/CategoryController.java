package com.BackendProject.Shopeeee.controller;


import com.BackendProject.Shopeeee.exception.AlreadyExistsException;
import com.BackendProject.Shopeeee.exception.ResourceNotFoundException;
import com.BackendProject.Shopeeee.model.Category;
import com.BackendProject.Shopeeee.response.ApiResponse;
import com.BackendProject.Shopeeee.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;


     @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){

        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("found",categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("error",HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){

        try {
            Category thecategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success",thecategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body( new ApiResponse(e.getMessage(),null));

        }

    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){

        try {
            Category theCategory = categoryService.getCategoryById(id);

            return ResponseEntity.ok(new ApiResponse("found",theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));


        }

    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){

        try {
            Category theCategory = categoryService.getCategoryByName(name);

            return ResponseEntity.ok(new ApiResponse("found",theCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }

    }

    @DeleteMapping("category/{id}/category")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){

        try {
             categoryService.deleteCategoryById(id);

            return ResponseEntity.ok(new ApiResponse("deleted",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));


        }

    }

    @GetMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category){

        try {
            Category updatedCategory = categoryService.updateCategory(category, id);

            return ResponseEntity.ok(new ApiResponse("updated",updatedCategory));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));


        }

    }



}
