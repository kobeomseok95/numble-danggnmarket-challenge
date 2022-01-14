package com.danggn.challenge.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    @DisplayName("이미지 추가 / 성공")
    void addProductImagesTest_success() throws Exception {

        // given
        Product product = Product.builder().build();
        List<ProductImage> productImages = List.of(
                ProductImage.builder().url("test1").build(),
                ProductImage.builder().url("test2").build());

        // when
        product.addProductImages(productImages);

        // then
        assertAll(
                () -> assertEquals(product.getProductImages().getSize(), productImages.size())
        );
    }
}
