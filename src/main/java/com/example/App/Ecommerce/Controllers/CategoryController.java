package com.example.App.Ecommerce.Controllers;

import com.example.App.Ecommerce.Consistents.AppConsistent;
import com.example.App.Ecommerce.Payload.CategoryDto;
import com.example.App.Ecommerce.Payload.ResDtos.CategoryRes;
import com.example.App.Ecommerce.Services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryRes> getCategories(
            @RequestParam(defaultValue = AppConsistent.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConsistent.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConsistent.SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConsistent.SORT_ORDER) String sortOrder
    ) {
        CategoryRes categories = categoryService.GetCategories(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/api/admin/category")
    public ResponseEntity<CategoryDto> CreateCategory(@Valid @RequestBody CategoryDto Category)
    {
        CategoryDto ResCategory = categoryService.Create(Category);
        return new ResponseEntity<>(ResCategory, HttpStatus.CREATED);
    }

    @PutMapping("/api/admin/Category/{Id}")
    public ResponseEntity<CategoryDto> UpdateCategory(@PathVariable Long Id, @RequestBody CategoryDto category)
    {
        CategoryDto ResCategory = categoryService.Update(category, Id);
        return new ResponseEntity<>(ResCategory, HttpStatus.OK);
    }


    @DeleteMapping("/api/admin/delete/Category/{Id}")
    public ResponseEntity<String> DeleteCategory(@PathVariable Long Id)
    {
        categoryService.delete(Id);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }

}
