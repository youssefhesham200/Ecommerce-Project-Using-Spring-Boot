package com.example.App.Ecommerce.Payload.ResDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Integer qty;
    private Integer discountPercentage;
    private BigDecimal discountAmount;
    private  String image;
}
