package com.danggn.challenge.product.domain.repository;

import com.danggn.challenge.product.domain.repository.vo.ProductsQuerydslDto;
import com.danggn.challenge.product.domain.repository.vo.ProductDetailQuerydslDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface ProductQuerydslRepository {

    Slice<ProductsQuerydslDto> findProducts(Pageable pageable);

    Optional<ProductDetailQuerydslDto> findProductDetail(Long productId);

    Slice<ProductsQuerydslDto> findLikeProducts(Long memberId, Pageable pageable);

    Slice<ProductsQuerydslDto> findByMemberId(Long memberId, Pageable pageable);
}
