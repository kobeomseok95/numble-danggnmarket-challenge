package com.danggn.challenge.product.domain;

import com.danggn.challenge.common.entity.BaseEntity;
import com.danggn.challenge.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Embedded
    private ProductImages productImages;

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory productCategory;

    @Column(nullable = false)
    private Long price;

    @Column(length = 1000, nullable = false)
    private String mainText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductTradeStatus productTradeStatus;

    @Embedded
    private Likes likes;

    private Long likesCount;

    private Long commentsCount;

    private String thumbnailImageUrl;

    public void addProductImages(List<ProductImage> productImages) {
        this.productImages = ProductImages.builder().build();
        this.productImages.addAll(productImages);
    }

    public void like(Like like) {
        likes.add(like);
        likesCount++;
    }

    public void unlike(Like like) {
        likes.remove(like);
        likesCount--;
    }

    public void updateStatus(String status) {
        productTradeStatus = ProductTradeStatus.valueOf(status);
    }

    public void updateInfoWithImageUrls(Product source, List<ProductImage> sourceImages) {
        name = source.getName();
        productCategory = source.getProductCategory();
        price = source.getPrice();
        mainText = source.getMainText();
        thumbnailImageUrl = sourceImages.get(0).getUrl();
        this.productImages.updateProductImages(sourceImages);
    }

    public void updateInfo(Product source) {
        name = source.getName();
        productCategory = source.getProductCategory();
        price = source.getPrice();
        mainText = source.getMainText();
    }

    public void addCommentsCount() {
        commentsCount++;
    }

    public void subCommentsCount() {
        commentsCount--;
    }
}
