package com.danggn.challenge.product.application.request;

import lombok.*;

@Value
@EqualsAndHashCode(of = {"productId"})
@Builder
public class UpdateProductTradeStatusRequestVo {

    private final Long productId;
    private final String status;
}
