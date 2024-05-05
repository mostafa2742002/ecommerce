package com.web.ecommerce.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public Iterable<Product> searchProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String subCategory,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(defaultValue = "title") String sortBy) {
        return productService.searchProducts(category, subCategory, brand, colors, sizes, sortBy);
    }

    @GetMapping("/discounted")
    public Iterable<Product> getDiscountedProducts() {
        return productService.getDiscountedProducts();
    }

    @GetMapping("/new")
    public Iterable<Product> getNewProducts() {
        return productService.getNewProducts();
    }

    @GetMapping("/popular")
    public Iterable<Product> getPopularProducts() {
        return productService.getPopularProducts();
    }

}
