package com.danggn.challenge.product.domain.repository;

import com.danggn.challenge.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p left join fetch p.productImages.values i where p.id = :productId")
    Optional<Product> findWithImageUrlsById(@Param("productId") Long productId);

    @Query("select p from Product p left join fetch p.likes.values l where p.id = :productId")
    Optional<Product> findWithLikesById(@Param("productId") Long productId);
}
