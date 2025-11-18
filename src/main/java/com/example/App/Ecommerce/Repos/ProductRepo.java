package com.example.App.Ecommerce.Repos;

import com.example.App.Ecommerce.Model.Category;
import com.example.App.Ecommerce.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllBycategory(Category category, Pageable pageable);
}
