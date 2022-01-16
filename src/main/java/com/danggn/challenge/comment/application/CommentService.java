package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.application.request.UpdateCommentRequestVo;
import com.danggn.challenge.comment.domain.Comment;
import com.danggn.challenge.comment.domain.repository.CommentJpaRepository;
import com.danggn.challenge.common.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
class CommentService implements CommentUseCase {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentApplicationAssembler commentApplicationAssembler;

    @Override
    public Long save(CreateCommentRequestVo createCommentRequestVo, LoginMember loginMember) {
        Comment comment = commentApplicationAssembler.toCommentEntity(
                createCommentRequestVo,
                loginMember.getMember()
        );
        commentJpaRepository.save(comment);
        return createCommentRequestVo.getProductId();
    }

    @Override
    public Long update(UpdateCommentRequestVo updateCommentRequestVo) {
        Comment comment = commentJpaRepository.findById(updateCommentRequestVo.getCommentId())
                .orElseThrow();
        comment.updateContents(updateCommentRequestVo.getContents());
        return updateCommentRequestVo.getProductId();
    }

    @Override
    public void delete(Long commentId) {
        commentJpaRepository.deleteById(commentId);
    }
}
