package com.danggn.challenge.product.domain.repository;

import com.danggn.challenge.product.domain.Product;

import java.util.Optional;

interface ProductQuerydslRepository {

    Optional<Product> findWithImageUrlsById(Long id);
}
