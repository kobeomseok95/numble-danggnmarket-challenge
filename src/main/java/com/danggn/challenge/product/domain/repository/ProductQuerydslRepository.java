package com.danggn.challenge.product.domain.repository;

import com.danggn.challenge.product.domain.repository.dto.ProductDetailQuerydslDto;
import com.danggn.challenge.product.domain.repository.dto.ProductsQuerydslDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductQuerydslRepository {

    List<ProductsQuerydslDto> findProducts(Pageable pageable);

    Optional<ProductDetailQuerydslDto> findProductDetail(Long productId);

    List<ProductsQuerydslDto> findLikeProducts(Long memberId, Pageable pageable);

    List<ProductsQuerydslDto> findByMemberIdStatus(Long memberId, String productStatusName, Pageable pageable);
}
