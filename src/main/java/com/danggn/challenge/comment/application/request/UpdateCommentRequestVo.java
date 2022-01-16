package com.danggn.challenge.comment.application.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = {"commentId", "contents"})
@Builder
public class UpdateCommentRequestVo {

    private final Long commentId;
    private final Long productId;
    private final String contents;
}
