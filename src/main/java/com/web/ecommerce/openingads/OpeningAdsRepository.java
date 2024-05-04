package com.web.ecommerce.openingads;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpeningAdsRepository extends MongoRepository<OpeningAds, String> {

}
