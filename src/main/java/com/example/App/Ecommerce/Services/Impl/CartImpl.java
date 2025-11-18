package com.example.App.Ecommerce.Services.Impl;

import com.example.App.Ecommerce.Consistents.enums.CartStatus;
import com.example.App.Ecommerce.Exceptions.ApiException;
import com.example.App.Ecommerce.Model.Cart;
import com.example.App.Ecommerce.Model.CartItem;
import com.example.App.Ecommerce.Model.Product;
import com.example.App.Ecommerce.Model.User;
import com.example.App.Ecommerce.Payload.ProductDto;
import com.example.App.Ecommerce.Payload.ResDtos.CartResDto;
import com.example.App.Ecommerce.Payload.ResDtos.ProductRes;
import com.example.App.Ecommerce.Repos.CartRepo;
import com.example.App.Ecommerce.Repos.ProductRepo;
import com.example.App.Ecommerce.Repos.UserRepo;
import com.example.App.Ecommerce.Services.CartService;
import com.example.App.Ecommerce.security.services.AuthUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Service
public class CartImpl implements CartService {
    private final AuthUtils authUtils;
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;
    private final CartRepo cartRepo;

    public CartImpl(CartRepo cartRepo, AuthUtils authUtils, ProductRepo productRepo, ModelMapper modelMapper, CartRepo cartRepo1) {
        this.authUtils = authUtils;
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.cartRepo = cartRepo1;
    }


    @Override
    public CartResDto addCartItem(Long productId, Integer qty) {
        User user = authUtils.getUser();

        Product product = productRepo.findById(productId).
                orElseThrow(() -> new ApiException("product not exist"));

        if(qty > product.getStockQuantity())
        {
            throw new ApiException("product qty not enough");
        }

        Cart cart = user.getCart();

        if(cart == null)
        {
            cart = new Cart();
            cart.setUser(user);
        }

        double discountFloat = (double) product.getDiscountPercentage() / 100;
        BigDecimal discountAmount = product.getPrice().multiply(BigDecimal.valueOf(discountFloat));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(qty);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.setDiscountAmount(discountAmount);
        cart.addItem(cartItem);

        cartRepo.save(cart);

        CartResDto cartResDto = modelMapper.map(cart, CartResDto.class);

        cartResDto.setProductList(cart.getCartItems().stream()
                .map(cartItem1 -> {
                           ProductDto productDto = modelMapper.map(cartItem1.getProduct(), ProductDto.class);
                           productDto.setQty(cartItem1.getQuantity());
                           productDto.setDiscountAmount(cartItem1.getDiscountAmount());
                           return  productDto;})
                .toList()
        );

        return cartResDto;
    }

    @Override
    public void deleteCartItem(Long productId) {

    }
}
