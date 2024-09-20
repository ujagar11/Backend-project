package com.BackendProject.Shopeeee.service.product;

import com.BackendProject.Shopeeee.dto.ImageDto;
import com.BackendProject.Shopeeee.dto.ProductDto;
import com.BackendProject.Shopeeee.exception.AlreadyExistsException;
import com.BackendProject.Shopeeee.exception.ProductNotFoundException;
import com.BackendProject.Shopeeee.model.Category;
import com.BackendProject.Shopeeee.model.Image;
import com.BackendProject.Shopeeee.model.Product;
import com.BackendProject.Shopeeee.repository.CategoryRepository;
import com.BackendProject.Shopeeee.repository.ImageRepository;
import com.BackendProject.Shopeeee.repository.ProductRepository;
import com.BackendProject.Shopeeee.request.AddProductRequest;
import com.BackendProject.Shopeeee.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        //check if category is found in the database
        //if yes set it as the new product category
        //if no then save it as new category
        //then set as the new product category.

        if (productExists(request.getName(), request.getBrand())){
            throw new AlreadyExistsException(request.getBrand()+" "+request.getName()+" already exists, you may update this product");
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {

                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

             request.setCategory(category);
             return productRepository.save(createProduct(request,category));
    }

    private Boolean productExists(String name,String brand){

        return productRepository.existsByNameAndBrand(name,brand);
    }

    private  Product createProduct(AddProductRequest request, Category category){

     return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
             request.getInventory(),
             request.getDescription(),
             category
     );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse((productRepository::delete),
                        ()-> {throw new ProductNotFoundException("product not found");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {

      return productRepository.findById(productId)
              .map(existingProduct ->updateExistingProduct(existingProduct,request))
              .map(productRepository ::save)
              .orElseThrow(()-> new ProductNotFoundException("Product no found"));

    }
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());

       existingProduct.setCategory(category);
        return existingProduct;


    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getAllProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getAllProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getAllProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){

        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product,ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imagesDtos = images.stream().
                map(image -> modelMapper.map(image,ImageDto.class)).toList();

        productDto.setImages(imagesDtos);
        return productDto;

    }

}
