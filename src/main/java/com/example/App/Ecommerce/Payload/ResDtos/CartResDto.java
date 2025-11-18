package com.example.App.Ecommerce.Payload.ResDtos;

import com.example.App.Ecommerce.Consistents.enums.CartStatus;
import com.example.App.Ecommerce.Payload.ProductDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartResDto {
    private Long id;

    private BigDecimal TotalAmount;

    private List<ProductDto> productList = new ArrayList<>();
}
