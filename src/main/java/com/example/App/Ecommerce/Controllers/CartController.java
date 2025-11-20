package com.example.App.Ecommerce.Controllers;

import com.example.App.Ecommerce.Payload.ResDtos.CartResDto;
import com.example.App.Ecommerce.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/api/cart/product/{productId}/qty/{qty}")
    public ResponseEntity<CartResDto> addCartItem(@PathVariable Long productId, @PathVariable Integer qty)
    {
        CartResDto cartResDto = cartService.addCartItem(productId, qty);
        return ResponseEntity.ok().body(cartResDto);
    }

    @DeleteMapping("/api/cart/product/{productId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long productId)
    {
        cartService.deleteCartItem(productId);
        return ResponseEntity.ok().body("deleted Successfully");
    }

    @GetMapping("/api/carts/users/cart")
    public ResponseEntity<CartResDto> getCart()
    {
        CartResDto cartResDto = cartService.getCart();
        return ResponseEntity.ok().body(cartResDto);
    }

    @PutMapping("/api/cart/products/{productId}/quantity/{operation}")
    public ResponseEntity<CartResDto> updateProductQty(@PathVariable Long productId,
                                                       @PathVariable String operation)
    {
        Integer qty = operation.equalsIgnoreCase("delete") ? -1 : 1;
        CartResDto cartResDto = cartService.updateProductQty(productId, qty);
        return ResponseEntity.ok().body(cartResDto);
    }

}
