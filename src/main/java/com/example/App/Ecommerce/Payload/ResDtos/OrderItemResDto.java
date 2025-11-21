package com.example.App.Ecommerce.Payload.ResDtos;

import com.example.App.Ecommerce.Model.Order;
import com.example.App.Ecommerce.Model.Product;
import com.example.App.Ecommerce.Payload.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemResDto {
    private Long id;

    private BigDecimal unitPrice;

    private BigDecimal discountAmount;

    private Integer quantity;

    private ProductDto product;
}
