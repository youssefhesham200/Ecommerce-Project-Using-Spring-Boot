package com.example.App.Ecommerce.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name="Categories")
@Data
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;

    @OneToMany(mappedBy = "category")
    List<Product> products = new ArrayList<>();
}
