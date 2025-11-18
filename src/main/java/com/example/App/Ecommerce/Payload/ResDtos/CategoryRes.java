package com.example.App.Ecommerce.Payload.ResDtos;

import com.example.App.Ecommerce.Payload.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CategoryRes {
     private List<CategoryDto> Categories;
     private Integer pageNumber;
     private Integer pageSize;
     private  boolean lasePage;
     private  Long totalElements;
}
