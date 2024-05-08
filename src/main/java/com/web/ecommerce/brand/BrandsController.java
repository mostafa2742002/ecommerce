package com.web.ecommerce.brand;

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
public class BrandsController {

    @Autowired
    private BrandsService brandsService;

    @PostMapping("/brand")
    public Brand addBrand(@RequestBody @NotNull @Valid Brand brand) {
        return brandsService.saveBrand(brand);
    }

    @DeleteMapping("/brand")
    public void deleteBrandById(@RequestParam @NotNull @Valid String id) {
        brandsService.deleteBrandById(id);
    }

    @PutMapping("/brand")
    public Brand updateBrand(@RequestBody @NotNull @Valid Brand brand) {
        return brandsService.updateBrand(brand);
    }

    @GetMapping("/brand")
    public Brand getBrandById(@RequestParam @NotNull String id) {
        return brandsService.getBrandById(id);
    }

    @GetMapping("/brands")
    public Iterable<Brand> getBrands() {
        return brandsService.getBrands();
    }
}
