package com.example.App.Ecommerce.Services.Impl;

import com.example.App.Ecommerce.Exceptions.ApiException;
import com.example.App.Ecommerce.Model.Cart;
import com.example.App.Ecommerce.Model.CartItem;
import com.example.App.Ecommerce.Model.Product;
import com.example.App.Ecommerce.Model.User;
import com.example.App.Ecommerce.Payload.ProductDto;
import com.example.App.Ecommerce.Payload.ResDtos.CartItemResDto;
import com.example.App.Ecommerce.Payload.ResDtos.CartResDto;
import com.example.App.Ecommerce.Repos.CartItemRepo;
import com.example.App.Ecommerce.Repos.CartRepo;
import com.example.App.Ecommerce.Repos.ProductRepo;
import com.example.App.Ecommerce.Services.CartService;
import com.example.App.Ecommerce.security.services.AuthUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartImpl implements CartService {
    private final AuthUtils authUtils;
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;
    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;

    public CartImpl(CartRepo cartRepo, AuthUtils authUtils, ProductRepo productRepo, ModelMapper modelMapper, CartRepo cartRepo1, CartItemRepo cartItemRepo) {
        this.authUtils = authUtils;
        this.productRepo = productRepo;
        this.modelMapper = modelMapper;
        this.cartRepo = cartRepo1;
        this.cartItemRepo = cartItemRepo;
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

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(qty);
        cartItem.setUnitPrice(product.getPrice());
        cartItem.updateDiscount(qty);
        cart.addItem(cartItem);

        cartRepo.save(cart);

        CartResDto cartResDto = modelMapper.map(cart, CartResDto.class);

        cartResDto.setCartItemResDtoArrayList(cart.getCartItems().stream()
                .map(cartItem1 -> {
                           CartItemResDto cartItemResDto = modelMapper.map(cartItem1.getProduct(), CartItemResDto.class);
                           cartItemResDto.setQty(cartItem1.getQuantity());
                           cartItemResDto.setDiscountAmount(cartItem1.getDiscountAmount());
                           return  cartItemResDto;})
                .toList()
        );

        return cartResDto;
    }

    @Override
    public void deleteCartItem(Long productId) {
        User user = authUtils.getUser();
        Cart cart =  user.getCart();

        if(cart == null)
        {
            throw new ApiException("there's no cart related to this user");
        }

        cart.removeItem(cartItemRepo.findByCartIdAndProductId(cart.getId(), productId));
        cartRepo.save(cart);
    }

    @Override
    public CartResDto getCart()
    {
        User user = authUtils.getUser();
        Cart cart =  user.getCart();

        if(cart == null)
        {
            throw new ApiException("there's no cart related to this user");
        }

        CartResDto cartResDto = modelMapper.map(cart, CartResDto.class);

        cartResDto.setCartItemResDtoArrayList(cart.getCartItems().stream()
                .map(cartItem1 -> {
                    CartItemResDto cartItemResDto = modelMapper.map(cartItem1.getProduct(), CartItemResDto.class);
                    cartItemResDto.setQty(cartItem1.getQuantity());
                    cartItemResDto.setDiscountAmount(cartItem1.getDiscountAmount());
                    return  cartItemResDto;})
                .toList()
        );

        return cartResDto;
    }

    @Override
    public CartResDto updateProductQty(Long productId, Integer qty) {
        User user = authUtils.getUser();
        Cart cart =  user.getCart();

        if(cart == null)
        {
            throw new ApiException("there's no cart related to this user");
        }

        Product product = productRepo.findById(productId).
                orElseThrow(() -> new ApiException("product not exist"));

        CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cart.getId(), productId);

        int newQty = cartItem.getQuantity() + qty;

        if(newQty > product.getStockQuantity())
            throw new ApiException("product qty not enough");

        if(newQty == 0)
        {
            cart.removeItem(cartItem);
        }
        else
        {
            cartItem.setQuantity(newQty);
            cartItem.updateDiscount(newQty);
            cartItemRepo.save(cartItem);
            cart.calculateTotals();
        }

        cartRepo.save(cart);

        CartResDto cartResDto = modelMapper.map(cart, CartResDto.class);

        cartResDto.setCartItemResDtoArrayList(cart.getCartItems().stream()
                .map(cartItem1 -> {
                    CartItemResDto cartItemResDto = modelMapper.map(cartItem1.getProduct(), CartItemResDto.class);
                    cartItemResDto.setQty(cartItem1.getQuantity());
                    cartItemResDto.setDiscountAmount(cartItem1.getDiscountAmount());
                    return  cartItemResDto;})
                .toList()
        );

        return  cartResDto;
    }
}
