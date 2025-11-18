package com.example.App.Ecommerce.Controllers;

import com.example.App.Ecommerce.Consistents.enums.RoleType;
import com.example.App.Ecommerce.Model.Role;
import com.example.App.Ecommerce.Model.User;
import com.example.App.Ecommerce.Repos.RoleRepo;
import com.example.App.Ecommerce.Repos.UserRepo;
import com.example.App.Ecommerce.security.JwtUtils;
import com.example.App.Ecommerce.security.payload.LoginRequest;
import com.example.App.Ecommerce.security.payload.LoginResponse;
import com.example.App.Ecommerce.security.payload.MessageRes;
import com.example.App.Ecommerce.security.payload.SignUpRequest;
import com.example.App.Ecommerce.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.*;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @PostMapping("/api/auth/signin")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest)
    {
        Authentication authentication;

        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            Map<String, Object> res = new HashMap<>();
            res.put("message", "Bad credentials");
            res.put("status", false);
            return new ResponseEntity<Object>(res, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new ResponseEntity<Object>(new LoginResponse(userDetails.getId(), jwtToken, userDetails.getUsername(), roles), HttpStatus.OK);
    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<?> SignUp(@RequestBody SignUpRequest signUpRequest)
    {
        if(userRepo.existsByUsername(signUpRequest.getUsername()))
        {
            return  ResponseEntity.badRequest().body(new MessageRes("username is already exists"));
        }

        if(userRepo.existsByEmail(signUpRequest.getEmail()))
        {
            return  ResponseEntity.badRequest().body(new MessageRes("Email is already exists"));
        }

        User user = new User(
                signUpRequest.getName(),
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail()
        );

        Set<String> StrRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if(signUpRequest.getRoles() == null)
        {
            Role userRole = roleRepo.findByRoleType(RoleType.User).
                    orElseThrow(() -> new RuntimeException("role not exist"));

            roles.add(userRole);
        }
        else
        {
            StrRoles.
                    forEach
                    (
                        role -> {
                            switch (role.toLowerCase(Locale.ROOT)) {
                                case "admin":
                                    Role adminRole = roleRepo.findByRoleType(RoleType.Admin).
                                            orElseThrow(() -> new RuntimeException("role not exist"));
                                    roles.add(adminRole);
                                    break;
                                case "seller":
                                    Role SellerRole = roleRepo.findByRoleType(RoleType.Seller).
                                            orElseThrow(() -> new RuntimeException("role not exist"));
                                    roles.add(SellerRole);
                                    break;
                                default:
                                    Role userRole = roleRepo.findByRoleType(RoleType.User).
                                            orElseThrow(() -> new RuntimeException("role not exist"));
                                    roles.add(userRole);
                                    break;
                            }
                        });
        }

        user.setRoles(roles);
        userRepo.save(user);

        return ResponseEntity.ok().body(new MessageRes("user registered"));
    }


    @GetMapping("/api/auth/user")
    public ResponseEntity<?> getUser(Authentication authentication) throws UserPrincipalNotFoundException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepo.findById(userDetails.getId())
                .orElseThrow(() -> new UserPrincipalNotFoundException(userDetails.getName()));

        return ResponseEntity.ok().body(user);
    }
}
