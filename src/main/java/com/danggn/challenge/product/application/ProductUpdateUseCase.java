package com.danggn.challenge.product.application;

import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductTradeStatusRequestVo;

public interface ProductUpdateUseCase {

    void updateProductStatus(UpdateProductTradeStatusRequestVo updateProductTradeStatusRequestVo);

    Long updateProductInfo(UpdateProductInfoRequestVo updateProductInfoRequestVo);

    void deleteProduct(Long id);
}
