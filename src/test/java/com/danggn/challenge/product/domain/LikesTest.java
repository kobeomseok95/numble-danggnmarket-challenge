package com.danggn.challenge.product.domain;

import com.danggn.challenge.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LikesTest {

    @Test
    @DisplayName("좋아요 갯수 가져오기 / 성공")
    void getSize_success() throws Exception {

        // given
        Likes likes = Likes.builder()
                .values(List.of(
                        Like.builder().id(1L).build(),
                        Like.builder().id(2L).build()))
                .build();

        // when, then
        assertEquals(likes.getValues().size(), likes.getSize());
    }

    @Test
    @DisplayName("좋아요 추가 / 성공")
    void addLike_success() throws Exception {

        // given
        Product product = Product.builder()
                .id(1L)
                .likes(Likes.builder().build())
                .build();
        Like like = Like.builder()
                .member(Member.builder().id(2L).build())
                .product(product)
                .build();
        Likes likes = Likes.builder().build();

        // when
        likes.add(like);

        // then
        assertEquals(1, likes.getValues().size());
    }

    @Test
    @DisplayName("좋아요 추가 / 실패 - 이미 좋아요 한 경우")
    void addLike_shouldNotDuplicateMemberProduct() throws Exception {

        // given
        Product product = Product.builder()
                .id(1L)
                .likes(Likes.builder().build())
                .build();
        Like like = Like.builder()
                .member(Member.builder().id(2L).build())
                .product(product)
                .build();
        Likes likes = Likes.builder()
                .values(List.of(like))
                .build();

        // when, then
        assertThrows(IllegalArgumentException.class,
                () -> likes.add(like));
    }

    @Test
    @DisplayName("좋아요 취소 / 성공")
    void removeLike_success() throws Exception {

        // given
        Product product = Product.builder()
                .id(1L)
                .likes(Likes.builder().build())
                .build();
        Like like = Like.builder()
                .member(Member.builder().id(2L).build())
                .product(product)
                .build();
        Likes likes = product.getLikes();
        likes.getValues().add(like);

        // when
        likes.remove(like);

        // then
        assertAll(
                () -> assertEquals(0, likes.getSize()),
                () -> assertThat(likes.getValues()).doesNotContain(like)
        );
    }
}
