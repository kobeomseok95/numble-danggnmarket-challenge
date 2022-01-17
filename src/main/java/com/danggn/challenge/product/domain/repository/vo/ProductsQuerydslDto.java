package com.danggn.challenge.product.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductsQuerydslDto {

    private Long productId;
    private String thumbnailImageUrl;
    private String productName;
    private String productTradeStatus;
    private Long price;
    private Long commentCount;
    private Long likeCount;

    @QueryProjection
    public ProductsQuerydslDto(Long productId, String thumbnailImageUrl, String productName, String productTradeStatus, Long price, Long commentCount, Long likeCount) {
        this.productId = productId;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.productName = productName;
        this.productTradeStatus = productTradeStatus;
        this.price = price;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }
}
