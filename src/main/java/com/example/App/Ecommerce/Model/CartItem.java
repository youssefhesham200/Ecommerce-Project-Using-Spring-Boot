package com.example.App.Ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity(name = "cart_items")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;


    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ✅ Null-safe calculation
    public BigDecimal getItemTotal() {
        BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        return subtotal.subtract(discountAmount); // Ensure non-negative
    }

    public void updateDiscount(Integer quantity)
    {
        if(product.getDiscountPercentage() != null && product.getDiscountPercentage() > 0)
        {
            double discountFloat = (double) product.getDiscountPercentage() / 100;
            BigDecimal discountAmount = product.getPrice().multiply(BigDecimal.valueOf(discountFloat));

            if(discountAmount.compareTo(BigDecimal.ZERO) > 0)
            {
                this.discountAmount = discountAmount.multiply(BigDecimal.valueOf(quantity));
            }
        }
    }

}