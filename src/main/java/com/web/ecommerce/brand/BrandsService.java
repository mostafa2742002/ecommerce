package com.web.ecommerce.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandsService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand getBrandById(String id) {
        return brandRepository.findById(id).orElse(null);
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteBrandById(String id) {
        brandRepository.deleteById(id);
    }

    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }

}
