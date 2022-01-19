package com.danggn.challenge.comment.application.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentResponseVo {

    private final Long commentId;
    private final Long productId;
    private final String contents;
}
