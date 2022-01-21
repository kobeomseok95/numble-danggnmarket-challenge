package com.danggn.challenge.product.application;

import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.response.ProductDetailResponseVo;
import com.danggn.challenge.product.application.response.ProductsResponseVo;
import com.danggn.challenge.product.application.usecase.ProductQueryUseCase;
import com.danggn.challenge.product.domain.repository.ProductQuerydslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ProductQueryService implements ProductQueryUseCase {

    private final ProductQuerydslRepository productQuerydslRepository;

    @Override
    public Slice<ProductsResponseVo> findProducts(Pageable pageable) {
        return ProductApplicationAssembler.toProductsResponseVoSlice(
                productQuerydslRepository.findProducts(pageable),
                pageable);
    }

    @Override
    public ProductDetailResponseVo findProductDetail(Long productId) {
        return ProductApplicationAssembler.toProductDetailResponseVo(
                productQuerydslRepository.findProductDetail(productId)
                        .orElseThrow());
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

    @Override
    public UpdateProductInfoRequestVo findProductInfo(Long productId) {
        return ProductApplicationAssembler.toUpdateProductInfoResponseVo(
                productQuerydslRepository.findWithImageUrlById(productId).orElseThrow());
    }

    @Override
    public List<Long> getLikeProductIds(LoginMember loginMember) {
        return productQuerydslRepository.findLikeProductIdsByMemberId(loginMember.getMemberId());
    }
}
