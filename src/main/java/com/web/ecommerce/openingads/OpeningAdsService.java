package com.web.ecommerce.openingads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpeningAdsService {

    @Autowired
    private OpeningAdsRepository openingAdsRepository;

    public OpeningAds getOpeningAdsById(String id) {
        return openingAdsRepository.findById(id).orElse(null);
    }

    public OpeningAds saveOpeningAds(OpeningAds openingAds) {
        return openingAdsRepository.save(openingAds);
    }

    public void deleteOpeningAdsById(String id) {
        openingAdsRepository.deleteById(id);
    }

    public OpeningAds updateOpeningAds(OpeningAds openingAds) {
        return openingAdsRepository.save(openingAds);
    }
}
