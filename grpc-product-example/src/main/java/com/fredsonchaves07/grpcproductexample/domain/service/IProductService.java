package com.fredsonchaves07.grpcproductexample.domain.service;

import com.fredsonchaves07.grpcproductexample.domain.dto.ProductDTO;

public interface IProductService {
    String create(ProductDTO inputDTO);
    ProductDTO findById(String productId);
}
