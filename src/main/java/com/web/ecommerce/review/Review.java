package com.web.ecommerce.review;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    @NotNull(message = "Product id cannot be empty")
    private String productId;

    @NotNull(message = "User id cannot be empty")
    private String userId;

    @NotNull(message = "Rating cannot be empty")
    private double rating;

    @NotNull(message = "Review cannot be empty")
    private String review;

}
