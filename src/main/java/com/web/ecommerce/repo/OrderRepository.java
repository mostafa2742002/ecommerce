package com.web.ecommerce.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.web.ecommerce.model.Order;



public interface OrderRepository extends MongoRepository <Order, String>{
    
}
