package com.danggn.challenge.product.application;

import com.danggn.challenge.common.security.LoginMember;

public interface ProductLikeUseCase {

    void like(Long productId, LoginMember loginMember);

    void unlike(Long productId, LoginMember loginMember);
}
