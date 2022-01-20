package com.danggn.challenge.product.application.usecase;

import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.response.ProductDetailResponseVo;
import com.danggn.challenge.product.application.response.ProductsResponseVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductQueryUseCase {

    Slice<ProductsResponseVo> findProducts(Pageable pageable);

    ProductDetailResponseVo findProductDetail(Long productId);

    Slice<ProductsResponseVo> findLikeProducts(Long memberId, Pageable pageable);

    Slice<ProductsResponseVo> findByMemberId(Long memberId, String productStatusName, Pageable pageable);

    UpdateProductInfoRequestVo findProductInfo(Long productId);
}
