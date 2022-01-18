package com.danggn.challenge.comment.application.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class CommentsResponseVo {

    private final Long productId;
    private final String thumbnailImageUrl;
    private final String productName;
    private final String productTradeStatus;
    private final Long price;
    @Builder.Default
    private final List<CommentVo> comments = new ArrayList<>();

    @Value
    @Builder
    public static class CommentVo {

        private final Long memberId;
        private final String memberNickname;
        private final String memberProfileUrl;
        private final LocalDateTime createdDate;
        private final String contents;
    }
}
