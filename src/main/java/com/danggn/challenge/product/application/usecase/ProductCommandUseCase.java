package com.danggn.challenge.product.application.usecase;

import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductTradeStatusRequestVo;

public interface ProductCommandUseCase {

    Long save(CreateProductRequestVo createProductRequestVo, LoginMember loginMember);

    void updateProductStatus(UpdateProductTradeStatusRequestVo updateProductTradeStatusRequestVo);

    Long updateProductInfo(UpdateProductInfoRequestVo updateProductInfoRequestVo);

    void deleteProduct(Long id);

    Long like(Long productId, LoginMember loginMember);

    Long unlike(Long productId, LoginMember loginMember);
}
