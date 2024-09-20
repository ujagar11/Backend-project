package com.BackendProject.Shopeeee.controller;


import com.BackendProject.Shopeeee.dto.ProductDto;
import com.BackendProject.Shopeeee.exception.AlreadyExistsException;
import com.BackendProject.Shopeeee.exception.ResourceNotFoundException;
import com.BackendProject.Shopeeee.model.Product;
import com.BackendProject.Shopeeee.request.AddProductRequest;
import com.BackendProject.Shopeeee.request.ProductUpdateRequest;
import com.BackendProject.Shopeeee.response.ApiResponse;
import com.BackendProject.Shopeeee.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){

        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
    }

    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);

            return ResponseEntity.ok(new ApiResponse("success",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            ProductDto productDto = productService.convertToDto(theProduct);

            return ResponseEntity.ok(new ApiResponse("Add product success",productDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct (@RequestBody ProductUpdateRequest request , @PathVariable Long productId){

        try {
            Product newProduct = productService.updateProduct(request,productId);
            ProductDto productDto = productService.convertToDto(newProduct);
            return ResponseEntity.ok(new ApiResponse("updated",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct (@PathVariable Long productId){

        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Deleted",productId));
        } catch (ResourceNotFoundException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName,@RequestParam String productName){

           try {
               List<Product> products = productService.getAllProductsByBrandAndName(brandName, productName);
               if (!products.isEmpty()) {
                   List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                   return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
               } else {
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
               }
           }catch (Exception e){
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
           }
    }


    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String categoryName,@RequestParam String brandName){

        try {
            List<Product> products = productService.getAllProductsByCategoryAndBrand(categoryName, brandName);

            if (!products.isEmpty()) {
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/product/{productName}/products")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String productName){

        try {
            List<Product> products = productService.getProductsByName(productName);

            if (!products.isEmpty()) {
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brandName){

        try {
            List<Product> products = productService.getAllProductsByBrand(brandName);

            if (!products.isEmpty()) {
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/product/{categoryName}/all/products")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String categoryName){

        try {
            List<Product> products = productService.getAllProductsByCategory(categoryName);

            if (!products.isEmpty()) {
                List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
                return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @GetMapping("/product/count/by/brand-and-name/")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName,@RequestParam String productName){

        try {
            Long productsCount = productService.countProductByBrandAndName(brandName, productName);
            if (productsCount != 0) {
                return ResponseEntity.ok(new ApiResponse("product count", productsCount));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("not found", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }


    
}
