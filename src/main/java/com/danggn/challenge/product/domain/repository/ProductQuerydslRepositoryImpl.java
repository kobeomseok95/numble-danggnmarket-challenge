package com.danggn.challenge.product.domain.repository;

import com.danggn.challenge.product.domain.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Product> findWithImageUrlsById(Long id) {
        return Optional.empty();
    }
}
