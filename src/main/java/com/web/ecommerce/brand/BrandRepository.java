package com.web.ecommerce.brand;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {

    Brand findByName(String name);

    Brand findByLogo(String logo);

}
