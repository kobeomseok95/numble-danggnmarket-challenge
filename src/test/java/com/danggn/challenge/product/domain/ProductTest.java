package com.danggn.challenge.product.domain;

import com.danggn.challenge.member.domain.Member;
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

    @Test
    @DisplayName("좋아요 리스트 추가 / 성공")
    void like_success() throws Exception {

        // given
        Product product = Product.builder()
                .id(1L)
                .likes(Likes.builder().build())
                .build();
        Like like = Like.builder().product(product).build();

        // when
        product.addLike(like);

        // then
        List<Like> values = product.getLikes().getValues();
        assertAll(
                () -> assertEquals(1, values.size()),
                () -> assertEquals(like, values.get(0))
        );
    }

    @Test
    @DisplayName("좋아요 리스트 삭제 / 성공")
    void unlike_success() throws Exception {

        // given
        Product product = Product.builder()
                .id(1L)
                .likes(Likes.builder().build())
                .build();
        Member member = Member.builder().id(1L).build();
        Like like = Like.builder()
                .product(product)
                .member(member)
                .build();
        product.getLikes().getValues().add(like);

        // when
        product.removeLike(like);

        // then
        List<Like> values = product.getLikes().getValues();
        assertEquals(0, values.size());
    }
}
