package com.web.ecommerce.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    // addProduct, removeProduct, updateProduct, getProduct, getProducts(fitered by
    // category, price, etc.)
    @Autowired
    private ProductRepository productRepository;

    public ArrayList<Product> getPopularProducts() {
        List<Product> Products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "buyed"));

        ArrayList<Product> popularProducts = new ArrayList<Product>();

        for (int i = 0; i < 10; i++) {
            popularProducts.add(Products.get(i));
        }

        return popularProducts;
    }

    public ArrayList<Product> getNewProducts() {
        List<Product> Products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        ArrayList<Product> newProducts = new ArrayList<Product>();

        for (int i = 0; i < 10; i++) {
            newProducts.add(Products.get(i));
        }

        return newProducts;
    }

    public ArrayList<Product> getDiscountedProducts() {
        List<Product> Products = productRepository.findAll();

        ArrayList<Product> discountedProducts = new ArrayList<Product>();

        for (Product product : Products) {
            if (product.getDiscount()) {
                discountedProducts.add(product);
            }
        }

        return discountedProducts;
    }

    // // filter by catigory and subcatigory and brand and size and color and sorted
    // by price or rating or buyed asc or desc for all products
    public ArrayList<Product> searchProducts(String category, String subCategory, String brandId, List<String> colors,
            List<String> sizes, String sortBy) {
        List<Product> Products = productRepository.findAll();

        ArrayList<Product> filteredProducts = new ArrayList<Product>();

        for (Product product : Products) {
            if (category != null && !product.getCategory().equals(category)) {
                continue;
            }

            if (subCategory != null && !product.getSub_category().equals(subCategory)) {
                continue;
            }

            if (brandId != null && !product.getBrand_id().equals(brandId)) {
                continue;
            }

            if (colors != null && !colors.isEmpty() && !product.getColor().containsAll(colors)) {
                continue;
            }

            if (sizes != null && !sizes.isEmpty() && !product.getSizes().containsAll(sizes)) {
                continue;
            }

            filteredProducts.add(product);
        }

        if (sortBy.equals("price")) {
            filteredProducts.sort((Product p1, Product p2) -> p1.getPrice().compareTo(p2.getPrice()));
        } else if (sortBy.equals("rating")) {
            filteredProducts.sort((Product p1, Product p2) -> Double.compare(p1.getRating(), p2.getRating()));
        } else if (sortBy.equals("buyed")) {
            filteredProducts.sort((Product p1, Product p2) -> Integer.compare(p1.getBuyed(), p2.getBuyed()));
        }

        return filteredProducts;
    }
}
