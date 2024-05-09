package com.web.ecommerce.user;

import java.util.List;

import com.web.ecommerce.product.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {

    List<Product> products;
    int subTotal;
    int shipping;
}
