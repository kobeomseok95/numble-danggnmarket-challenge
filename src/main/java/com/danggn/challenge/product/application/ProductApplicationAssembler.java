package com.danggn.challenge.product.application;

import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.domain.Product;
import com.danggn.challenge.product.domain.ProductCategory;
import com.danggn.challenge.product.domain.ProductImage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductApplicationAssembler {

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
}
