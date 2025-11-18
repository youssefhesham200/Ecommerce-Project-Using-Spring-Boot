package com.example.App.Ecommerce.Payload;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class CategoryDto {
    Long id;
    @NotBlank
    String name;
}


