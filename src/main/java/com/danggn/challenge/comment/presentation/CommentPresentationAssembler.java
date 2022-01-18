package com.danggn.challenge.comment.presentation;

import com.danggn.challenge.comment.application.request.CreateCommentRequestVo;
import com.danggn.challenge.comment.presentation.request.CreateCommentRequest;
import org.springframework.stereotype.Component;

@Component
class CommentPresentationAssembler {

    static CreateCommentRequestVo toCreateCommentRequestVo(CreateCommentRequest request) {
        return CreateCommentRequestVo.builder()
                .productId(request.getProductId())
                .contents(request.getContents())
                .build();
    }
}
