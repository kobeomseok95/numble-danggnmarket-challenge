package com.danggn.challenge.product.domain.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UpdateProductInfoQuerydslDto {

    private Long productId;
    private List<String> imageUrls;
    private String name;
    private String category;
    private Long price;
    private String mainText;

    @QueryProjection
    public UpdateProductInfoQuerydslDto(Long productId, List<String> imageUrls, String name, String category, Long price, String mainText) {
        this.productId = productId;
        this.imageUrls = imageUrls;
        this.name = name;
        this.category = category;
        this.price = price;
        this.mainText = mainText;
    }
}
