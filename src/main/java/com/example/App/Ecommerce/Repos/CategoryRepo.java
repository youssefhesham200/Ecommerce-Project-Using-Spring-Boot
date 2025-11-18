package com.example.App.Ecommerce.Repos;

import com.example.App.Ecommerce.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
     Optional<Category> findByName(String name);
}
