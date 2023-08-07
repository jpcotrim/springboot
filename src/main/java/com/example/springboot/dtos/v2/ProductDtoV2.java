package com.example.springboot.dtos.v2;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

@JsonPropertyOrder({"name, productId, price, description, _links"})
public class ProductDtoV2 {

    private UUID productId;

    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productID) {
        this.productId = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}