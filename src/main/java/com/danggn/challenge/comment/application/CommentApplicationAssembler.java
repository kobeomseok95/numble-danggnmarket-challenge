package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.domain.Comment;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CommentApplicationAssembler {

    public static Comment toCommentEntity(Product product, Member member, CreateCommentRequestVo createCommentRequestVo) {
        return Comment.builder()
                .member(member)
                .product(product)
                .contents(createCommentRequestVo.getContents())
                .build();
    }
}
