package com.example.App.Ecommerce.Payload;

import com.example.App.Ecommerce.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDto
{
    private Integer id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phoneNumber;
}
