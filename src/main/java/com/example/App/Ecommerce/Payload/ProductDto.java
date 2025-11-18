package com.example.App.Ecommerce.Payload;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    Long id;
    String name;
    BigDecimal price;
    BigDecimal discountAmount;
    String description;
    Integer qty;
    String image;
}
