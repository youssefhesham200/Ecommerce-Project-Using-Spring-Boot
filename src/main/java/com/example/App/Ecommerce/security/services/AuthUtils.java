package com.example.App.Ecommerce.security.services;

import com.example.App.Ecommerce.Model.User;
import com.example.App.Ecommerce.Repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    @Autowired
    UserRepo userRepo;

    public  String getUserName ()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public  User getUser ()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByUsername(authentication.getName()).
                orElseThrow(() -> new UsernameNotFoundException("userName Not found"));
    }
}
