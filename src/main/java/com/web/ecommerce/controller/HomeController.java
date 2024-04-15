package com.web.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.ecommerce.model.Product;
import com.web.ecommerce.model.User;
import com.web.ecommerce.service.HomeService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/home")
    public List<Product> home() {
        return homeService.home();
    }

    @GetMapping("/home/resentllyadded")
    public List<Product> recentlyAdded() {
        return homeService.resentllyAdded();
    }

    @GetMapping("/home/topselling")
    public List<Product> topSelling() {
        return homeService.topSelling();
    }

    @PostMapping("/home/verifyemail")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return homeService.verifyEmail(token);
    }

    @GetMapping("/home/search")
    public ResponseEntity<List<Product>> search(@RequestParam String search) {
        return homeService.search(search);
    }

}
