package com.web.ecommerce.home;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.web.ecommerce.email.EmailService;
import com.web.ecommerce.jwt.JwtService;
import com.web.ecommerce.product.ProductRepository;
import com.web.ecommerce.product.Product;
import com.web.ecommerce.user.User;
import com.web.ecommerce.user.UserDetailsServiceImpl;
import com.web.ecommerce.user.UserRepository;
import com.web.ecommerce.user.UserService;

import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class HomeService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    ProductRepository ProductRepository;

    @Autowired
    UserRepository user_repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<List<Product>> home() {
        try {
            return ResponseEntity.ok(ProductRepository.findAll());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    public ResponseEntity<List<Product>> resentllyAdded() {
        try {
            List<Product> Products = ProductRepository.findAll();

            if (Products.size() > 4) {
                // return Products.subList(0, 4);
                return ResponseEntity.ok(Products.subList(0, 4));
            }

            return ResponseEntity.ok(Products);
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    public ResponseEntity<List<Product>> topSelling() {
        try {
            List<Product> Products = ProductRepository.findAll(Sort.by(Sort.Direction.DESC, "buyed"));

            if (Products.size() > 4) {
                return ResponseEntity.ok(Products.subList(0, 4));
            }

            return ResponseEntity.ok(Products);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }



    // public ResponseEntity<List<Product>> search(String search) {
    //     if (search == null || search.trim().isEmpty()) {
    //         return ResponseEntity.ok(Collections.emptyList());
    //     }

    //     // THE OLD WAY
    //     return ResponseEntity.ok(ProductRepository.findByTitleIgnoreCaseContainingOrCategoryIgnoreCaseContaining(search,
    //             search));

    //     // THE NEW WAY
    //     // return
    //     // ResponseEntity.ok(Product_elastic_repo.findByTitleOrAuthorOrCategoryOrTranslatorOrPublisher(search,
    //     // search,
    //     // search, search, search));
    // }

}
