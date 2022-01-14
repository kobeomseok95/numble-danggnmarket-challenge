package com.danggn.challenge.product.application;

import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;

public interface ProductUseCase {

    Long save(CreateProductRequestVo createProductRequestVo, LoginMember loginMember);
}
