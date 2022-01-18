package com.danggn.challenge.product.application.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "productId")
@Builder
public class ProductsResponseVo {

    private final Long productId;
    private final Long memberId;
    private final String thumbnailImageUrl;
    private final String productName;
    private final Long price;
    private final String productTradeStatus;
    private final Long commentCount;
    private final Long likeCount;
}
