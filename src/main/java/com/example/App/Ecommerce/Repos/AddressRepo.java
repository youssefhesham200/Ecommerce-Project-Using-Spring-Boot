package com.example.App.Ecommerce.Repos;

import com.example.App.Ecommerce.Model.Address;
import com.example.App.Ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    List<Address> findAllByUser(User user);
}
