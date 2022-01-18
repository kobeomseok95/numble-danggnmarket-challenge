package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.application.request.UpdateCommentRequestVo;
import com.danggn.challenge.comment.application.response.CommentsResponseVo;
import com.danggn.challenge.comment.domain.Comment;
import com.danggn.challenge.comment.domain.repository.CommentJpaRepository;
import com.danggn.challenge.comment.domain.repository.CommentQuerydslRepository;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.domain.Product;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
class CommentService implements CommentUseCase {

    private final ProductJpaRepository productJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final CommentQuerydslRepository commentQuerydslRepository;

    @Override
    public Long save(CreateCommentRequestVo createCommentRequestVo, LoginMember loginMember) {
        Product product = productJpaRepository.findById(createCommentRequestVo.getProductId())
                .orElseThrow();

        product.addCommentsCount();
        Comment comment = CommentApplicationAssembler.toCommentEntity(
                product, loginMember.getMember(), createCommentRequestVo
        );
        commentJpaRepository.save(comment);
        return product.getId();
    }

    @Override
    public Long update(UpdateCommentRequestVo updateCommentRequestVo) {
        Comment target = commentJpaRepository.findById(updateCommentRequestVo.getCommentId())
                .orElseThrow();
        target.updateContents(updateCommentRequestVo.getContents());
        return updateCommentRequestVo.getProductId();
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = commentJpaRepository.findWithProductById(commentId)
                .orElseThrow();
        comment.subCommentCount();
        commentJpaRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentsResponseVo getProductComments(Long productId) {
        return CommentApplicationAssembler
                .toCommentsResponseVo(commentQuerydslRepository.findCommentsByProductId(productId)
                .orElseThrow());
    }
}
