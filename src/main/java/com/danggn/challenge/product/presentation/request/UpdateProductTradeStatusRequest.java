package com.danggn.challenge.product.presentation.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductTradeStatusRequest {

    private String status;
}
