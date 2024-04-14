package com.web.ecommerce.service;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.web.ecommerce.model.Product;
import com.web.ecommerce.repo.ProductRepository;
import com.web.ecommerce.repo.UserRepository;

@Service
public class AdminService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository user_repo;

    public String addProduct(Product product) {
        productRepository.save(product);
        return "Product added successfully";
    }

    public String removeProduct(String product_id) {

        productRepository.deleteById(product_id);
        return "Product removed successfully";
    }

    public String updateProduct(Product product) {

        productRepository.save(product);
        return "Product updated successfully";
    }

}
