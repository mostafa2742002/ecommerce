package com.web.ecommerce.product;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.web.ecommerce.review.Review;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import lombok.Data;

@Data
@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @NotNull(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Sub category cannot be empty")
    private String sub_category;

    @NotNull(message = "Price cannot be empty")
    private Double price;

    @NotNull(message = "product information cannot be empty")
    private String product_information;

    private String image;

    @NotNull(message = "Brand id cannot be empty")
    private String brand;

    @NotNull(message = "Color name cannot be empty")
    private ArrayList<String> color;

    @NotNull(message = "Sizes cannot be empty")
    private ArrayList<String> sizes;

    private ArrayList<String> additional_images;

    @NotNull(message = "Delivery cannot be empty")
    private ArrayList<Delivery> delivery;

    private String size_guide;

    private double rating = 0.0;

    private ArrayList<Review> reviews;

    private int buyed = 0;

    private Boolean discount = false;

    private double discount_price = 0.0;

}
