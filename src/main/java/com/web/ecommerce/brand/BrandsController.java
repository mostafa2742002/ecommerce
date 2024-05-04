package com.web.ecommerce.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandsController {

    @Autowired
    private BrandsService brandsService;

    @PostMapping("/brand")
    public Brand addBrand(@RequestBody Brand brand) {
        return brandsService.saveBrand(brand);
    }

    @DeleteMapping("/brands")
    public void deleteBrandById(@RequestParam String id) {
        brandsService.deleteBrandById(id);
    }

    @PutMapping("/brand")
    public Brand updateBrand(@RequestBody Brand brand) {
        return brandsService.updateBrand(brand);
    }

}
