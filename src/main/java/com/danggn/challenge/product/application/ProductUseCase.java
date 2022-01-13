package com.danggn.challenge.product.application;

import com.danggn.challenge.product.application.request.CreateProductRequestVo;

public interface ProductUseCase {

    Long save(CreateProductRequestVo createProductRequestVo, Long memberId);
}
