package com.fredsonchaves07.grpcproductexample.domain.repository;

import com.fredsonchaves07.grpcproductexample.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
