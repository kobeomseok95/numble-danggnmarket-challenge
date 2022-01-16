package com.danggn.challenge.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductTradeStatus {

    SALE("판매중"),
    HOLD("예약중"),
    SOLD_OUT("판매완료");

    private final String description;
}
