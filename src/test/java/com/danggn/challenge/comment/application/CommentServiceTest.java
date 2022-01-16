package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.application.request.UpdateCommentRequestVo;
import com.danggn.challenge.comment.domain.Comment;
import com.danggn.challenge.comment.domain.repository.CommentJpaRepository;
import com.danggn.challenge.common.security.LoginMember;
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
class CommentServiceTest {

    @Mock
    CommentJpaRepository commentJpaRepository;
    @Mock
    CommentApplicationAssembler applicationAssembler;
    @Mock
    ProductJpaRepository productJpaRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    @DisplayName("댓글 생성 / 성공")
    void save_success() throws Exception {

        // given
        LoginMember loginMember = mock(LoginMember.class);
        CreateCommentRequestVo requestVo = createMockCreateCommentRequestVo();
        when(productJpaRepository.findById(requestVo.getProductId()))
                .thenReturn(Optional.of(Product.builder().id(requestVo.getProductId()).build()));

        // when
        Long redirectProductId = commentService.save(requestVo, loginMember);

        // then
        assertAll(
                () -> verify(commentJpaRepository).save(any(Comment.class))
        );
    }

    private CreateCommentRequestVo createMockCreateCommentRequestVo() {
        return CreateCommentRequestVo.builder()
                .productId(1L)
                .contents("테스트 댓글 입니다.")
                .build();
    }

    @Test
    @DisplayName("댓글 수정 / 성공")
    void update_success() throws Exception {

        // given
        UpdateCommentRequestVo requestVo = createMockUpdateCommentRequestVo();
        Comment comment = Comment.builder()
                .id(requestVo.getCommentId())
                .contents("변경 전 댓글")
                .build();
        when(commentJpaRepository.findById(requestVo.getCommentId()))
                .thenReturn(Optional.of(comment));

        // when
        Long redirectProductId = commentService.update(requestVo);

        // then
        assertAll(
                () -> verify(commentJpaRepository).findById(requestVo.getCommentId()),
                () -> assertEquals(requestVo.getContents(), comment.getContents())
        );
    }

    private UpdateCommentRequestVo createMockUpdateCommentRequestVo() {
        return UpdateCommentRequestVo.builder()
                .commentId(1L)
                .productId(2L)
                .contents("테스트 댓글 입니다.")
                .build();
    }

    @Test
    @DisplayName("댓글 삭제 / 성공")
    void delete_success() throws Exception {

        // given, when
        commentService.delete(1L);

        // then
        verify(commentJpaRepository).deleteById(1L);
    }
}
