package com.example.App.Ecommerce.Services;

import com.example.App.Ecommerce.Payload.ProductDto;
import com.example.App.Ecommerce.Payload.ResDtos.ProductRes;

public interface ProductService {
    ProductRes getProducts(Integer pageSize,Integer pageNumber,String sortBy,String sortOrder, Long categoryId);
    ProductDto createProduct(Long categoryId,ProductDto productDto);
    ProductDto updateProduct(Long productId,ProductDto productDto);
}
