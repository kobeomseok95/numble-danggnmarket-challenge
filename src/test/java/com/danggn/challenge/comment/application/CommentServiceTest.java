package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.domain.Comment;
import com.danggn.challenge.comment.domain.repository.CommentJpaRepository;
import com.danggn.challenge.common.security.LoginMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentJpaRepository commentJpaRepository;
    @Mock
    CommentApplicationAssembler applicationAssembler;

    @InjectMocks
    CommentService commentService;

    @Test
    @DisplayName("댓글 생성 / 성공")
    void save_success() throws Exception {

        // given
        LoginMember loginMember = mock(LoginMember.class);
        CreateCommentRequestVo requestVo = createMockCreateCommentRequestVo();
        when(applicationAssembler.toCommentEntity(requestVo, loginMember.getMember()))
                .thenReturn(Comment.builder().build());


        // when
        commentService.save(requestVo, loginMember);

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
}