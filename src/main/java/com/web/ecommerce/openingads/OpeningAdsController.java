package com.web.ecommerce.openingads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;



@RestController
public class OpeningAdsController {

    @Autowired
    private OpeningAdsService openingAdsService;

    @PostMapping("/openingAds")
    public OpeningAds addOpeningAds(@RequestBody @NotNull @Valid OpeningAds openingAds) {
        return openingAdsService.saveOpeningAds(openingAds);
    }

    @DeleteMapping("/OpeningAds")
    public void deleteOpeningAdsById(@RequestParam @NotNull String id) {
        openingAdsService.deleteOpeningAdsById(id);
    }

    @PutMapping("/OpeningAds")
    public OpeningAds updateOpeningAds(@RequestBody @NotNull @Valid OpeningAds openingAds) {
        return openingAdsService.updateOpeningAds(openingAds);
    }

    @GetMapping("/OpeningAds")
    public OpeningAds getOpeningAdsById(@RequestParam @NotNull String id) {
        return openingAdsService.getOpeningAdsById(id);
    }

}
