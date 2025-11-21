package com.example.App.Ecommerce.Controllers;

import com.example.App.Ecommerce.Consistents.enums.OrderStatus;
import com.example.App.Ecommerce.Model.User;
import com.example.App.Ecommerce.Payload.CreateOrderRequest;
import com.example.App.Ecommerce.Payload.ResDtos.OrderResDto;
import com.example.App.Ecommerce.Services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ========== USER ENDPOINTS ==========

    @PostMapping
    @PreAuthorize("hasAnyRole('User', 'Admin')")
    public ResponseEntity<OrderResDto> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        return new ResponseEntity<>(orderService.createOrder(request), HttpStatus.CREATED);
    }

}