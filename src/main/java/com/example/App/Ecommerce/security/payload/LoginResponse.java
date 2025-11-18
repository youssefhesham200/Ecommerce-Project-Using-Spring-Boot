package com.example.App.Ecommerce.security.payload;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private Long id;
    private String jwtToken;
    private String username;
    private List<String> roles;
}