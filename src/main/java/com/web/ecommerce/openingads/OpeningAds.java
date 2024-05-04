package com.web.ecommerce.openingads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpeningAds {

    private String image;
    private String product_id;
    private Double discount;
}
