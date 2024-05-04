package com.web.ecommerce.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getRatingsByProduct(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    public Review saveRating(Review rating) {
        return reviewRepository.save(rating);
    }
}
