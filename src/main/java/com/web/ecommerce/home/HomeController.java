package com.web.ecommerce.home;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.web.ecommerce.product.Product;
import com.web.ecommerce.user.User;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Cacheable("home")
    @GetMapping("/home")
    public ResponseEntity<List<Product>> home() {
        try {
            return homeService.home();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @Cacheable("recentlyAdded")
    @GetMapping("/home/recentlyadded")
    public ResponseEntity<List<Product>> recentlyAdded() {
        return homeService.resentllyAdded();
    }

    @Cacheable("topSelling")
    @GetMapping("/home/topselling")
    public ResponseEntity<List<Product>> topSelling() {
        return homeService.topSelling();
    }

    // @GetMapping("/home/search")
    // public ResponseEntity<List<Product>> search(@RequestParam String search) {
    //     return homeService.search(search);
    // }

}
