package com.example.App.Ecommerce.Repos;

import com.example.App.Ecommerce.Consistents.enums.RoleType;
import com.example.App.Ecommerce.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleType(RoleType roleType);
}