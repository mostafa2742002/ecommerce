package com.web.ecommerce.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.ecommerce.product.Product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody @NotNull @Valid Product product) {
        return ResponseEntity.ok(adminService.addProduct(product));
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> removeProduct(@RequestParam @NotNull String productId) {
        return ResponseEntity.ok(adminService.removeProduct(productId));
    }

    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@RequestBody @NotNull @Valid Product product) {
        return ResponseEntity.ok(adminService.updateProduct(product));
    }

    @GetMapping("/product")
    public ResponseEntity<?> getProduct(@RequestParam @NotNull String productId) {
        return ResponseEntity.ok(adminService.getProduct(productId));
    }
}
