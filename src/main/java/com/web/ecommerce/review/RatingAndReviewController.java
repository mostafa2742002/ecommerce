package com.web.ecommerce.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
public class RatingAndReviewController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/review")
    public Review addReview(@RequestBody @NotNull @Valid Review Review) {
        return ratingService.saveRating(Review);
    }

    @GetMapping("/review/{productId}")
    public List<Review> getReviewsByProduct(@PathVariable @NotNull  String productId) {
        return ratingService.getRatingsByProduct(productId);
    }
}
