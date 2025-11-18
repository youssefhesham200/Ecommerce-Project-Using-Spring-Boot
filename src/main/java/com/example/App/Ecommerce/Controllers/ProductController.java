package com.example.App.Ecommerce.Controllers;

import com.example.App.Ecommerce.Consistents.AppConsistent;
import com.example.App.Ecommerce.Payload.ProductDto;
import com.example.App.Ecommerce.Payload.ResDtos.ProductRes;
import com.example.App.Ecommerce.Services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("api/public/products")
    public ResponseEntity<ProductRes> getProducts(
            @RequestParam(defaultValue = AppConsistent.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConsistent.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConsistent.SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConsistent.SORT_ORDER) String sortOrder
    )
    {
        ProductRes productRes = productService.getProducts(pageSize, pageNumber, sortBy, sortOrder, -1L);
        return new ResponseEntity<>(productRes, HttpStatus.OK);
    }

    @GetMapping("api/public/{categoryId}/products")
    public ResponseEntity<ProductRes> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = AppConsistent.PAGE_SIZE) Integer pageSize,
            @RequestParam(defaultValue = AppConsistent.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(defaultValue = AppConsistent.SORT_BY) String sortBy,
            @RequestParam(defaultValue = AppConsistent.SORT_ORDER) String sortOrder
    )
    {
        ProductRes productRes = productService.getProducts(pageSize, pageNumber, sortBy, sortOrder, categoryId);
        return new ResponseEntity<>(productRes, HttpStatus.OK);
    }

    @PostMapping("api/admin/{categoryId}/products")
    public ResponseEntity<ProductDto> createProduct(
            @PathVariable Long categoryId,
            @RequestBody ProductDto productDto
            )
    {
        ProductDto productRes = productService.createProduct(categoryId, productDto);
        return new ResponseEntity<>(productRes, HttpStatus.CREATED);
    }

    @PutMapping("api/admin/products/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductDto productDto
    )
    {
        ProductDto productRes = productService.updateProduct(productId, productDto);
        return new ResponseEntity<>(productRes, HttpStatus.CREATED);
    }

}
