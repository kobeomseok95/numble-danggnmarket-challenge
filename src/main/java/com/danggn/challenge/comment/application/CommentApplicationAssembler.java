package com.danggn.challenge.comment.application;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.application.response.CommentResponseVo;
import com.danggn.challenge.comment.application.response.CommentsResponseVo;
import com.danggn.challenge.comment.domain.Comment;
import com.danggn.challenge.comment.domain.repository.dto.CommentsQuerydslDto;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class CommentApplicationAssembler {

    static Comment toCommentEntity(Product product, Member member, CreateCommentRequestVo createCommentRequestVo) {
        return Comment.builder()
                .member(member)
                .product(product)
                .contents(createCommentRequestVo.getContents())
                .build();
    }

    static CommentsResponseVo toCommentsResponseVo(CommentsQuerydslDto commentsQuerydslDto) {
        return CommentsResponseVo.builder()
                .productId(commentsQuerydslDto.getProductId())
                .thumbnailImageUrl(commentsQuerydslDto.getThumbnailImageUrl())
                .productName(commentsQuerydslDto.getProductName())
                .productTradeStatus(commentsQuerydslDto.getProductTradeStatus())
                .price(commentsQuerydslDto.getPrice())
                .comments(commentsQuerydslDto.getComments().stream()
                        .map(dto -> CommentsResponseVo.CommentVo.builder()
                                .commentId(dto.getCommentId())
                                .memberId(dto.getMemberId())
                                .memberNickname(dto.getMemberNickname())
                                .memberProfileUrl(dto.getMemberProfileUrl())
                                .createdDate(dto.getCreatedDate())
                                .contents(dto.getContents())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    static CommentResponseVo toCommentResponseVo(Comment comment) {
        return CommentResponseVo.builder()
                .commentId(comment.getId())
                .productId(comment.getProduct().getId())
                .contents(comment.getContents())
                .build();
    }
}
