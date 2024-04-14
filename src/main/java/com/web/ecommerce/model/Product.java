package com.web.ecommerce.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import lombok.Data;

@Data
@Document(collection = "products")
public class Product implements Serializable {

    @Id
    private String id;

    @NotNull(message = "Title cannot be empty")
    private String title;

    @NotNull(message = "Category cannot be empty")
    private String category;

    @NotNull(message = "Price cannot be empty")
    private String price;

    @NotNull(message = "product information cannot be empty")
    private String product_information;

    private ArrayList<String> variations;

    private String image;
    private int buyed;
}
