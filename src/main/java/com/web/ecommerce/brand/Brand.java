package com.web.ecommerce.brand;

import org.checkerframework.checker.units.qual.N;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection = "brands")
public class Brand {

    @Id
    private String id;

    @NotNull(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Logo cannot be empty")
    private String logo;

    @NotNull(message = "Description cannot be empty")
    private String description;

}
