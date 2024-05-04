package com.web.ecommerce.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product) {
        try {
            return ResponseEntity.ok(adminService.addProduct(product));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product");
        }
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> removeProduct(@RequestParam String productId) {
        try {
            return ResponseEntity.ok(adminService.removeProduct(productId));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }

    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@RequestBody @Valid Product product) {
        try {
            return ResponseEntity.ok(adminService.updateProduct(product));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product");
        }
    }
}
