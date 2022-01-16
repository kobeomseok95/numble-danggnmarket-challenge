package com.danggn.challenge.comment.application.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = {"productId", "contents"})
@Builder
public class CreateCommentRequestVo {

    private final Long productId;
    private final String contents;
}
