package com.web.ecommerce.model;


import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import lombok.Data;

@Data
@Document(collection = "orders")
public class Order implements Serializable {

    @Id
    private String id;
    private String user_id;
    private List<ProductQuantity> products; // Product_id, quantity
    private String address;
    private String phone;
    private String total;
    private String payment; // "cash" or "fawry"
    private String status;
}
