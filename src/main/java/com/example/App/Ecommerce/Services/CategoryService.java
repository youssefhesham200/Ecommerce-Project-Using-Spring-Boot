package com.example.App.Ecommerce.Services;

import com.example.App.Ecommerce.Payload.CategoryDto;
import com.example.App.Ecommerce.Payload.ResDtos.CategoryRes;


public interface CategoryService {
    CategoryRes GetCategories(Integer pageNumber , Integer pageSize, String sortBy, String sortOrder);
    CategoryDto Create(CategoryDto category);
    CategoryDto Update(CategoryDto category, Long Id);
    void delete(Long Id);
}
