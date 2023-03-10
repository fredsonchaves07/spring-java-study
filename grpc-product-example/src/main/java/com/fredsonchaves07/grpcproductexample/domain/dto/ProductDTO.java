package com.fredsonchaves07.grpcproductexample.domain.dto;

import com.fredsonchaves07.grpcproductexample.domain.entity.Product;

public record ProductDTO(String id, String name, String description, Double price) {

    public Product toProduct() {
        return new Product(id, name, description, price);
    }
}
