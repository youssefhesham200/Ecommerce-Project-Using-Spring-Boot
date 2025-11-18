package com.example.App.Ecommerce.Payload.ResDtos;

import com.example.App.Ecommerce.Payload.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRes {
    List<ProductDto> products;
    private Integer pageNumber;
    private Integer pageSize;
    private  boolean lasePage;
    private  Long totalElements;
}
