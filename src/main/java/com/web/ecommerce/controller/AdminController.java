package com.web.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.web.ecommerce.model.Product;
import com.web.ecommerce.service.AdminService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/product")
    public String addProduct(@RequestBody @Valid Product product) {
        return adminService.addProduct(product);
    }

    @DeleteMapping("/product")
    public String removeProduct(@RequestBody @NotNull String Product_id) {
        return adminService.removeProduct(Product_id);
    }

    @PutMapping("/product")
    public String updateProduct(@RequestBody @Valid Product product) {
        return adminService.updateProduct(product);
    }

}
