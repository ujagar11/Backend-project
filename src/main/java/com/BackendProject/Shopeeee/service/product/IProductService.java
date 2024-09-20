package com.BackendProject.Shopeeee.service.product;

import com.BackendProject.Shopeeee.dto.ProductDto;
import com.BackendProject.Shopeeee.model.Product;
import com.BackendProject.Shopeeee.request.AddProductRequest;
import com.BackendProject.Shopeeee.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);



    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();

    List<Product> getAllProductsByCategory(String category);
    List<Product> getAllProductsByBrand(String brand);
    List<Product> getAllProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);

    List<Product> getAllProductsByBrandAndName(String brand,String name);

    Long countProductByBrandAndName(String brand,String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
