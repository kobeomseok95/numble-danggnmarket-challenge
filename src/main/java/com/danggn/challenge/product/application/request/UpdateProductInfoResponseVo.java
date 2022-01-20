package com.danggn.challenge.product.application.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(of = {"productId"})
@Builder
public class UpdateProductInfoResponseVo {

    private final Long productId;

    private final List<String> imageUrls;

    private final String name;

    private final String category;

    private final Long price;

    private final String mainText;
}
