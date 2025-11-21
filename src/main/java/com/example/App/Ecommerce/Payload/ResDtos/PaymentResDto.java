package com.example.App.Ecommerce.Payload.ResDtos;

import com.example.App.Ecommerce.Consistents.enums.PaymentStatus;
import com.example.App.Ecommerce.Model.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentResDto {
    private Long id;
    private String paymentMethod;

    private String pgPaymentId;

    private String pgName;
    private String pgResponseMsg;

    private PaymentStatus pgStatus;
}
