package com.danggn.challenge.product.presentation.request;

import lombok.*;

@Getter
@AllArgsConstructor
public enum ProductStatusRequest {
    ALL, SALE, SOLD_OUT;
}
