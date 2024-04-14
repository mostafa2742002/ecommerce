package com.web.ecommerce.repo;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.web.ecommerce.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
    // find by title or category
    List<Product> findByTitleIgnoreCaseContainingOrCategoryIgnoreCaseContaining(String title, String category);
}
