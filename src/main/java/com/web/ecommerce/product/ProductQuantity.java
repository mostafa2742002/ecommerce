package com.web.ecommerce.product;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductQuantity implements Serializable {
    private String ProductId;
    private int quantity;
}
