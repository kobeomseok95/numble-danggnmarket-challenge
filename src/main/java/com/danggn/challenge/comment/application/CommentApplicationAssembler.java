package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.domain.Comment;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.domain.Product;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CommentApplicationAssembler {

    private final ProductJpaRepository productJpaRepository;

    public Comment toCommentEntity(
            CreateCommentRequestVo createCommentRequestVo,
            Member member
    ) {
        Product product = productJpaRepository.findById(createCommentRequestVo.getProductId())
                .orElseThrow();

        return Comment.builder()
                .member(member)
                .product(product)
                .contents(createCommentRequestVo.getContents())
                .build();
    }
}
