package com.example.App.Ecommerce.Payload.ResDtos;

import com.example.App.Ecommerce.Payload.ProductDto;
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

    private List<CartItemResDto> cartItemResDtoArrayList = new ArrayList<>();
}
