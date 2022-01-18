package com.danggn.challenge.product.application.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@EqualsAndHashCode(of = "productId")
@Builder
public class ProductDetailResponseVo {

    private final List<String> imageUrls;
    private final Long memberId;
    private final String memberProfileUrl;
    private final Long productId;
    private final String memberNickname;
    private final String productName;
    private final Long price;
    private final String category;
    private final LocalDateTime createdDate;
    private final String contents;
    @Builder.Default
    private final List<MemberSaleProductsVo> memberSaleProducts = new ArrayList<>();

    @Value
    @EqualsAndHashCode(of = "productId")
    @Builder
    public static class MemberSaleProductsVo {

        private final Long memberId;
        private final Long productId;
        private final String thumbnailUrl;
        private final String productName;
        private final Long price;
    }
}
