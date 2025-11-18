package com.example.App.Ecommerce.config;

import com.example.App.Ecommerce.Consistents.enums.RoleType;
import com.example.App.Ecommerce.Model.Category;
import com.example.App.Ecommerce.Model.Product;
import com.example.App.Ecommerce.Model.Role;
import com.example.App.Ecommerce.Repos.CategoryRepo;
import com.example.App.Ecommerce.Repos.ProductRepo;
import com.example.App.Ecommerce.Repos.RoleRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepo roleRepo;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    @PostConstruct
    public void init() {

        // ----------------------
        // 1️⃣ Insert Roles
        // ----------------------
        if (roleRepo.count() == 0) {
            roleRepo.save(new Role(null, RoleType.User));
            roleRepo.save(new Role(null, RoleType.Admin));
            roleRepo.save(new Role(null, RoleType.Seller));
            System.out.println("Roles inserted.");
        }

        // ----------------------
        // 2️⃣ Insert Categories
        // ----------------------
        if (categoryRepo.count() == 0) {
            Category electronics = new Category();
            electronics.setName("Electronics");
            categoryRepo.save(electronics);

            Category fashion = new Category();
            fashion.setName("Fashion");
            categoryRepo.save(fashion);

            Category home = new Category();
            home.setName("Home Appliances");
            categoryRepo.save(home);

            System.out.println("Categories inserted.");
        }

        // ----------------------
        // 3️⃣ Insert Products
        // ----------------------
        if (productRepo.count() == 0) {
            Category electronics = categoryRepo.findByName("Electronics")
                    .orElseThrow(() -> new RuntimeException("Electronics category not found"));

            Category fashion = categoryRepo.findByName("Fashion")
                    .orElseThrow(() -> new RuntimeException("Fashion category not found"));

            Product iphone = new Product();
            iphone.setName("iPhone 15");
            iphone.setPrice(new BigDecimal("1200.00"));
            iphone.setDiscountPercentage(10);
            iphone.setDescription("Latest Apple phone");
            iphone.setStockQuantity(50);
            iphone.setCategory(electronics);
            productRepo.save(iphone);

            Product samsungTv = new Product();
            samsungTv.setName("Samsung TV 55\"");
            samsungTv.setPrice(new BigDecimal("800.00"));
            samsungTv.setDiscountPercentage(15);
            samsungTv.setDescription("4K Smart TV");
            samsungTv.setStockQuantity(20);
            samsungTv.setCategory(electronics);
            productRepo.save(samsungTv);

            Product tshirt = new Product();
            tshirt.setName("T-Shirt");
            tshirt.setPrice(new BigDecimal("20.00"));
            tshirt.setDiscountPercentage(0);
            tshirt.setDescription("Cotton T-shirt");
            tshirt.setStockQuantity(100);
            tshirt.setCategory(fashion);
            productRepo.save(tshirt);

            System.out.println("Products inserted.");
        }
    }
}
