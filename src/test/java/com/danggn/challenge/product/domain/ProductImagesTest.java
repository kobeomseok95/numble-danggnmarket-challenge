package com.danggn.challenge.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductImagesTest {

    @Test
    @DisplayName("컬렉션 객체에 상품 이미지 저장 / 성공")
    void addAllTest_success() throws Exception {

        // given
        ProductImages productImagesObject = ProductImages.builder().build();
        List<ProductImage> productImages = List.of(
                ProductImage.builder().url("test1").build(),
                ProductImage.builder().url("test2").build());

        // when
        productImagesObject.addAll(productImages);

        // then
        assertEquals(productImagesObject.getValues(), productImages);
    }

    @Test
    @DisplayName("컬렉션 객체에 상품 이미지 사이즈 조회 / 성공")
    void getSizeTest_success() throws Exception {

        // given
        ProductImages productImagesObject = ProductImages.builder().build();
        List<ProductImage> productImages = List.of(
                ProductImage.builder().url("test1").build(),
                ProductImage.builder().url("test2").build());

        // when
        productImagesObject.addAll(productImages);

        // then
        long size = productImagesObject.getSize();
        assertEquals(size, productImages.size());
    }

    @Test
    @DisplayName("상품 이미지 변경 / 성공")
    void updateProductImages_success() throws Exception {

        // given
        ProductImages productImages = ProductImages.builder()
                .values(Collections.emptyList())
                .build();
        List<ProductImage> images = List.of(
                ProductImage.builder().url("변경완료1").build(),
                ProductImage.builder().url("변경완료2").build()
        );

        // when
        productImages.updateProductImages(images);

        // then
        assertEquals(images.size(), productImages.getValues().size());
    }
}
