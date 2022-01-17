package com.danggn.challenge.product.application.usecase;

import com.danggn.challenge.product.application.response.MainProductsResponseVo;
import com.danggn.challenge.product.application.response.ProductDetailResponseVo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductQueryUseCase {

    Slice<MainProductsResponseVo> getProducts(Pageable pageable);

    ProductDetailResponseVo getProductDetail(Long productId);
}
