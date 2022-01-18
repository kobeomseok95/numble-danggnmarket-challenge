package com.danggn.challenge.product.domain.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberSaleProductsQuerydslDto {

    private Long memberId;
    private Long productId;
    private String thumbnailUrl;
    private String productName;
    private Long price;

    @QueryProjection
    public MemberSaleProductsQuerydslDto(Long memberId, Long productId, String thumbnailUrl, String productName, Long price) {
        this.memberId = memberId;
        this.productId = productId;
        this.thumbnailUrl = thumbnailUrl;
        this.productName = productName;
        this.price = price;
    }
}
