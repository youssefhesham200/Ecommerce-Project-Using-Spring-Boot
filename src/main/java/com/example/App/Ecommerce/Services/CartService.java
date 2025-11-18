package com.example.App.Ecommerce.Services;


import com.example.App.Ecommerce.Payload.ResDtos.CartResDto;

public interface CartService {
    CartResDto addCartItem(Long productId, Integer qty);
    void deleteCartItem(Long productId);
}
