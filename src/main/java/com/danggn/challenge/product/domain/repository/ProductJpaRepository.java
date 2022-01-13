package com.danggn.challenge.product.domain.repository;

import com.danggn.challenge.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
}
