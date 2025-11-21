package com.example.App.Ecommerce.Services.Impl;

import com.example.App.Ecommerce.Consistents.enums.OrderStatus;
import com.example.App.Ecommerce.Consistents.enums.PaymentStatus;
import com.example.App.Ecommerce.Model.*;
import com.example.App.Ecommerce.Payload.CreateOrderRequest;
import com.example.App.Ecommerce.Payload.ResDtos.OrderResDto;
import com.example.App.Ecommerce.Repos.*;
import com.example.App.Ecommerce.Services.AddressService;
import com.example.App.Ecommerce.Services.OrderService;
import com.example.App.Ecommerce.security.services.AuthUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderImpl implements OrderService {
    private final AddressRepo addressRepo;
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final ModelMapper modelMapper;
    private final AuthUtils authUtils;
    private final PaymentRepo paymentRepo;

    public OrderImpl(AddressRepo addressRepo, ProductRepo productRepo, OrderRepo orderRepo, CartRepo cartRepo, ModelMapper modelMapper, AuthUtils authUtils, PaymentRepo paymentRepo) {
        this.addressRepo = addressRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.modelMapper = modelMapper;
        this.authUtils = authUtils;
        this.paymentRepo = paymentRepo;
    }

    @Transactional
    @Override
    public OrderResDto createOrder(CreateOrderRequest request)
    {
        User user = authUtils.getUser();

        Cart cart = user.getCart();

        if (cart.getCartItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");
        }

        // Validate stock
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Insufficient stock for: " + item.getProduct().getName());
            }
        }

        // Create payment
        Payment payment = new Payment();
        payment.setPgStatus(PaymentStatus.PENDING);
        payment.setPgPaymentId(request.getPgPaymentId());
        payment.setPgName(request.getPgName());
        paymentRepo.save(payment);

        // get Address
        Address address = addressRepo.findById(request.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "address not found"));

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalAmount());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setShippingStreet(address.getStreet());
        order.setShippingCity(address.getCity());
        order.setShippingState(address.getState());
        order.setShippingCountry(address.getCountry());
        order.setShippingPostalCode(address.getPostalCode());
        order.setShippingPhone(address.getPhoneNumber());
        order.setCreatedAt(LocalDateTime.now());
        order.setPayment(payment);

        // Convert cart items to order items & reduce stock
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setDiscountAmount(cartItem.getDiscountAmount() != null
                    ? cartItem.getDiscountAmount() : BigDecimal.ZERO);
            order.getOrderItems().add(orderItem);

            // Reduce stock
            Product product = cartItem.getProduct();
            product.setStockQuantity(product.getStockQuantity()- cartItem.getQuantity());
            productRepo.save(product);
        }

        Order saved = orderRepo.save(order);

        // Clear cart
        cart.getCartItems().clear();
        cart.calculateTotals();
        cartRepo.save(cart);

        return modelMapper.map(
                saved, OrderResDto.class
        );
    }
}
