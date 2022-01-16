package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.domain.Comment;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.domain.Product;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentApplicationAssemblerTest {

    @Mock
    ProductJpaRepository productJpaRepository;

    @InjectMocks
    CommentApplicationAssembler applicationAssembler;

    @Test
    @DisplayName("comment 객체 생성 / 성공")
    void toCommentEntity_success() throws Exception {

        // given
        CreateCommentRequestVo requestVo = createMockCreateCommentRequestVo();
        Member member = mock(Member.class);
        Product product = Product.builder().id(requestVo.getProductId()).build();
        when(productJpaRepository.findById(requestVo.getProductId()))
                .thenReturn(Optional.of(product));

        // when
        Comment comment = applicationAssembler.toCommentEntity(
                requestVo,
                member
        );

        // then
        assertAll(
                () -> assertEquals(member, comment.getMember()),
                () -> assertEquals(product, comment.getProduct()),
                () -> assertEquals(requestVo.getContents(), comment.getContents())
        );
    }

    private CreateCommentRequestVo createMockCreateCommentRequestVo() {
        return CreateCommentRequestVo.builder()
                .productId(1L)
                .contents("테스트 댓글 입니다.")
                .build();
    }
}