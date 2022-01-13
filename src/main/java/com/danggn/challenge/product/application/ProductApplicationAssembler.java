package com.danggn.challenge.product.application;

import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.domain.Product;
import com.danggn.challenge.product.domain.ProductCategory;
import com.danggn.challenge.product.domain.ProductImage;
import com.danggn.challenge.product.domain.ProductImages;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductApplicationAssembler {

    // TODO 구현
    public Product toProductEntity(
            CreateProductRequestVo createProductRequestVo,
            Long memberId,
            List<String> imageFileUrls
    ) {
        return Product.builder()
                .member(Member.builder().id(memberId).build())
                .productImages(ProductImages.builder()
                        .productImages(
                                imageFileUrls.stream()
                                        .map(url -> ProductImage.builder()
                                                .url(url)
                                                .build())
                                        .collect(Collectors.toList()))
                        .build())
                .name(createProductRequestVo.getName())
                .productCategory(ProductCategory.valueOf(createProductRequestVo.getCategory()))
                .price(createProductRequestVo.getPrice())
                .mainText(createProductRequestVo.getMainText())
                .build();
    }
}
