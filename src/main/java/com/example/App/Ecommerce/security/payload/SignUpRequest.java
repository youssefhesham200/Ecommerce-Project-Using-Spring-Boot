package com.example.App.Ecommerce.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpRequest {
    private String name;
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}
