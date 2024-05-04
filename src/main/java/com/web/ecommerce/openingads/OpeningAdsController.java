package com.web.ecommerce.openingads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class OpeningAdsController {

    @Autowired
    private OpeningAdsService openingAdsService;

    @PostMapping("/openingAds")
    public OpeningAds addOpeningAds(@RequestBody OpeningAds openingAds) {
        return openingAdsService.saveOpeningAds(openingAds);
    }

    @DeleteMapping("/OpeningAds")
    public void deleteOpeningAdsById(@RequestParam String id) {
        openingAdsService.deleteOpeningAdsById(id);
    }

    @PutMapping("/OpeningAds")
    public OpeningAds updateOpeningAds(@RequestBody OpeningAds openingAds) {
        return openingAdsService.updateOpeningAds(openingAds);
    }

    @GetMapping("/OpeningAds")
    public OpeningAds getOpeningAdsById(@RequestParam String id) {
        return openingAdsService.getOpeningAdsById(id);
    }

}
