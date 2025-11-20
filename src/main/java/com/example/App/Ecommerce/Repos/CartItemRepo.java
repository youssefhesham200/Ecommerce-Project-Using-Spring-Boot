package com.example.App.Ecommerce.Repos;

import com.example.App.Ecommerce.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long>
{
    CartItem findByCartIdAndProductId(Long cartId, Long productid);
}
