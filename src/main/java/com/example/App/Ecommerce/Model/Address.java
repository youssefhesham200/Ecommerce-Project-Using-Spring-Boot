package com.example.App.Ecommerce.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Data
@Table(name = "addresses")
@ToString
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "address_id")
    private Integer id;

    @Column(name = "street", length = 100)
    private String street;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "state",  length = 50)
    private String state;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    User user;
}

