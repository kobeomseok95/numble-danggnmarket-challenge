package com.danggn.challenge.product.domain.repository.vo;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDetailQuerydslDto {

    private List<String> imageUrls;
    private Long memberId;
    private Long productId;
    private String memberNickname;
    private String productName;
    private Long price;
    private String category;
    private LocalDateTime createdDate;
    private String contents;
    private List<MemberSaleProductsQuerydslDto> memberSaleProducts;

    @QueryProjection
    public ProductDetailQuerydslDto(List<String> imageUrls, Long memberId, Long productId, String memberNickname, String productName, Long price, String category, LocalDateTime createdDate, String contents) {
        this.imageUrls = imageUrls;
        this.memberId = memberId;
        this.productId = productId;
        this.memberNickname = memberNickname;
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.createdDate = createdDate;
        this.contents = contents;
        this.memberSaleProducts = new ArrayList<>();
    }

    public void setMemberSaleProduct(List<MemberSaleProductsQuerydslDto> memberSaleProductDto) {
        this.memberSaleProducts.addAll(memberSaleProductDto);
    }
}
