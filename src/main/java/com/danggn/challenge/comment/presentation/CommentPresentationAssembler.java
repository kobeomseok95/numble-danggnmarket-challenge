package com.danggn.challenge.comment.presentation;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.application.request.UpdateCommentRequestVo;
import com.danggn.challenge.comment.application.response.CommentResponseVo;
import com.danggn.challenge.comment.presentation.request.CreateCommentRequest;
import com.danggn.challenge.comment.presentation.request.UpdateCommentRequest;
import org.springframework.stereotype.Component;

@Component
class CommentPresentationAssembler {

    static CreateCommentRequestVo toCreateCommentRequestVo(CreateCommentRequest request) {
        return CreateCommentRequestVo.builder()
                .productId(request.getProductId())
                .contents(request.getContents())
                .build();
    }

    static UpdateCommentRequest toUpdateCommentRequest(CommentResponseVo comment) {
        return UpdateCommentRequest.builder()
                .commentId(comment.getCommentId())
                .productId(comment.getProductId())
                .contents(comment.getContents())
                .build();
    }

    public static UpdateCommentRequestVo toUpdateCommentRequestVo(UpdateCommentRequest request) {
        return UpdateCommentRequestVo.builder()
                .commentId(request.getCommentId())
                .productId(request.getProductId())
                .contents(request.getContents())
                .build();
    }
}
