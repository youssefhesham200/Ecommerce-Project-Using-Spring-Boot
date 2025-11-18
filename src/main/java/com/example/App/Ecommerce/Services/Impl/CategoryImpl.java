package com.example.App.Ecommerce.Services.Impl;

import com.example.App.Ecommerce.Exceptions.ApiException;
import com.example.App.Ecommerce.Model.Category;
import com.example.App.Ecommerce.Payload.CategoryDto;
import com.example.App.Ecommerce.Payload.ResDtos.CategoryRes;
import com.example.App.Ecommerce.Repos.CategoryRepo;
import com.example.App.Ecommerce.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    public CategoryImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryRes GetCategories(Integer pageNumber , Integer pageSize, String sortBy, String sortOrder) {
        Sort sort =  sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> pageCategories= categoryRepo.findAll(pageable);

        List<CategoryDto> categories = pageCategories.getContent().stream().map(c ->
                modelMapper.map(c, CategoryDto.class)
        ).toList();

        if(categories.isEmpty()) throw new ApiException("no categories are found");

        return new CategoryRes(categories, pageCategories.getNumber(), pageCategories.getSize(), pageCategories.isLast(), pageCategories.getTotalElements());
    }

    @Override
    public CategoryDto Create(CategoryDto category) {
        Optional<Category> exCategory = categoryRepo.findByName(category.getName());
        if(exCategory.isPresent()) throw new ApiException("can't create Category with same existing name");
        Category savedCategory = categoryRepo.save(modelMapper.map(category, Category.class));
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto Update(CategoryDto categoryDto, Long id) {
        return categoryRepo.findById(id)
                .map(existing -> {
                    existing.setName(categoryDto.getName());
                    return modelMapper.map(categoryRepo.save(existing), CategoryDto.class);
                })
                .orElseThrow(() -> new ApiException("There's no category with this ID"));
    }


    @Override
    public void delete(Long Id)
    {
        Optional<Category> category = categoryRepo.findById(Id);

        if(category.isEmpty()) throw new ApiException("there's no category with this id");

        categoryRepo.delete(category.get());
    }
}
