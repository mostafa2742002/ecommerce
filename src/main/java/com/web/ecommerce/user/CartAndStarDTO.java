package com.web.ecommerce.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartAndStarDTO {

    @NotNull
    private String user_id;
    @NotNull
    private String product_id;
}
