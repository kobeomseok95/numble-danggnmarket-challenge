package com.danggn.challenge.product.application;

import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.domain.*;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class ProductApplicationAssembler {

    private final ProductJpaRepository productJpaRepository;

    public Product toProductEntity(
            CreateProductRequestVo createProductRequestVo,
            Member member
    ) {
        return Product.builder()
                .member(member)
                .name(createProductRequestVo.getName())
                .productCategory(ProductCategory.valueOf(createProductRequestVo.getCategory()))
                .price(createProductRequestVo.getPrice())
                .mainText(createProductRequestVo.getMainText())
                .productTradeStatus(ProductTradeStatus.SALE)
                .likes(Likes.builder().build())
                .build();
    }

    public List<ProductImage> toProductImageEntity(Product product, List<String> urls) {
        return urls.stream()
                .map(url -> ProductImage.builder()
                        .product(product)
                        .url(url)
                        .build())
                .collect(Collectors.toList());
    }

    public Like toLikeEntity(Product product, Member member) {
        return Like.builder()
                .product(product)
                .member(member)
                .build();
    }
}
