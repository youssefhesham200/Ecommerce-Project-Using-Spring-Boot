package com.example.App.Ecommerce.Services;

import com.example.App.Ecommerce.Model.User;
import com.example.App.Ecommerce.Payload.CreateOrderRequest;
import com.example.App.Ecommerce.Payload.ResDtos.OrderResDto;
import jakarta.transaction.Transactional;


public interface OrderService {
    @Transactional
    OrderResDto createOrder(CreateOrderRequest request);
}
