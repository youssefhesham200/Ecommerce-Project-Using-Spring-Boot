package com.example.App.Ecommerce.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
    private Long addressId;
    private String paymentMethod;
    private String pgName;
    private  String pgPaymentId;
}
