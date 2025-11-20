package com.example.App.Ecommerce.Services.Impl;

import com.example.App.Ecommerce.Consistents.enums.ProductStatus;
import com.example.App.Ecommerce.Exceptions.ApiException;
import com.example.App.Ecommerce.Model.Category;
import com.example.App.Ecommerce.Model.Product;
import com.example.App.Ecommerce.Payload.CategoryDto;
import com.example.App.Ecommerce.Payload.ProductDto;
import com.example.App.Ecommerce.Payload.ResDtos.CategoryRes;
import com.example.App.Ecommerce.Payload.ResDtos.ProductRes;
import com.example.App.Ecommerce.Repos.CategoryRepo;
import com.example.App.Ecommerce.Repos.ProductRepo;
import com.example.App.Ecommerce.Services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;
    private final CategoryRepo categoryRepo;

    public ProductImpl(ProductRepo productRepo, ModelMapper modelMapper, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public ProductRes getProducts(Integer pageSize, Integer pageNumber, String sortBy, String sortOrder, Long categoryId) {
        Sort sort =  sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> pageProducts;


        if(categoryId != -1)
        {
            Category category =  categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new ApiException("There's no category with this ID"));

            pageProducts = productRepo.findAllByCategoryAndProductStatus(category, ProductStatus.ACTIVE, pageable);
        }
        else
        {
            pageProducts = productRepo.findAllByProductStatus(ProductStatus.ACTIVE, pageable);
        }

        List<ProductDto> products = pageProducts.getContent().stream().map(c ->
                modelMapper.map(c, ProductDto.class)
        ).toList();

        if(products.isEmpty()) throw new ApiException("no products are found");

        return new ProductRes(products, pageProducts.getNumber(), pageProducts.getSize(), pageProducts.isLast(), pageProducts.getTotalElements());
    }

    @Override
    public ProductDto createProduct(Long categoryId, ProductDto productDto) {
        Category category =  categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ApiException("There's no category with this ID"));

        Product product = modelMapper.map(productDto, Product.class);
        product.setCategory(category);
        Product savedProduct= productRepo.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product existingProduct = productRepo.findById(productId)
                .orElseThrow(() -> new ApiException("There's no product with this ID"));

        modelMapper.map(productDto, existingProduct);

        Product updatedProduct = productRepo.save(existingProduct);

        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).
                orElseThrow(() -> new RuntimeException("product not found"));

        product.setProductStatus(ProductStatus.REMOVED);
        productRepo.save(product);
    }
}
