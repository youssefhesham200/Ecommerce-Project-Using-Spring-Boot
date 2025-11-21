package com.example.App.Ecommerce.Model;

import com.example.App.Ecommerce.Consistents.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "payments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(mappedBy = "payment", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Order order;

    private String paymentMethod;

    private String pgPaymentId;

    private String pgName;
    private String pgResponseMsg;

    @Enumerated(EnumType.STRING)
    private PaymentStatus pgStatus;
}
