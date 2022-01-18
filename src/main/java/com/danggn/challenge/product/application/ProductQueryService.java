package com.danggn.challenge.product.application;

import com.danggn.challenge.product.application.response.ProductDetailResponseVo;
import com.danggn.challenge.product.application.response.ProductsResponseVo;
import com.danggn.challenge.product.application.usecase.ProductQueryUseCase;
import com.danggn.challenge.product.domain.repository.ProductQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService implements ProductQueryUseCase {

    private final ProductQuerydslRepository productQuerydslRepository;

    @Override
    public Slice<ProductsResponseVo> getProducts(Pageable pageable) {
        return ProductApplicationAssembler.toProductsResponseVoSlice(
                productQuerydslRepository.findProducts(pageable),
                pageable);
    }

    @Override
    public ProductDetailResponseVo getProductDetail(Long productId) {
        return ProductApplicationAssembler.toProductDetailResponseVo(
                productQuerydslRepository.findProductDetail(productId)
                        .orElseThrow()
        );
    }

    @Override
    public Slice<ProductsResponseVo> findLikeProducts(Long memberId, Pageable pageable) {
        return ProductApplicationAssembler.toProductsResponseVoSlice(
                productQuerydslRepository.findLikeProducts(memberId, pageable),
                pageable);
    }

    @Override
    public Slice<ProductsResponseVo> findByMemberId(Long memberId, String productStatusName, Pageable pageable) {
        return ProductApplicationAssembler.toProductsResponseVoSlice(
                productQuerydslRepository.findByMemberIdStatus(memberId, productStatusName, pageable),
                pageable);
    }
}
