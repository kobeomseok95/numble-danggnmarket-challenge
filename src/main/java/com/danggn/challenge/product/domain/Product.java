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

    public void addProductImages(List<ProductImage> productImages) {
        this.productImages = ProductImages.builder().build();
        this.productImages.addAll(productImages);
    }

    public void addLike(Like like) {
        likes.add(like);
    }

    public void removeLike(Like like) {
        likes.remove(like);
    }

    public void updateStatus(String status) {
        productTradeStatus = ProductTradeStatus.valueOf(status);
    }

    public void updateInfo(Product target, List<ProductImage> productImages) {
        name = target.getName();
        productCategory = target.getProductCategory();
        price = target.getPrice();
        mainText = target.getMainText();
        this.productImages.updateProductImages(productImages);
    }
}
