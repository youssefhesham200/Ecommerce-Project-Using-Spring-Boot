package com.example.App.Ecommerce.Payload.ResDtos;

import com.example.App.Ecommerce.Consistents.enums.OrderStatus;
import com.example.App.Ecommerce.Model.OrderItem;
import com.example.App.Ecommerce.Model.Payment;
import com.example.App.Ecommerce.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResDto {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;

    //snapshot of ordered address
    private String shippingStreet;
    private String shippingCity;
    private String shippingState;
    private String shippingCountry;
    private String shippingPostalCode;
    private String shippingPhone;

    private String trackingNumber;

    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;
    private PaymentResDto payment;

    private List<OrderItemResDto> orderItems = new ArrayList<>();
}
