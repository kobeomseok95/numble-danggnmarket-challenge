package com.danggn.challenge.comment.domain.repository.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsQuerydslDto {

    private Long productId;
    private String thumbnailImageUrl;
    private String productName;
    private String productTradeStatus;
    private Long price;
    private List<CommentDto> comments;

    @QueryProjection
    public CommentsQuerydslDto(Long productId, String thumbnailImageUrl, String productName, String productTradeStatus, Long price) {
        this.productId = productId;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.productName = productName;
        this.productTradeStatus = productTradeStatus;
        this.price = price;
        this.comments = new ArrayList<>();
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class CommentDto {

        private Long memberId;
        private String memberNickname;
        private String memberProfileUrl;
        private LocalDateTime createdDate;
        private String contents;

        @QueryProjection
        public CommentDto(Long memberId, String memberNickname, String memberProfileUrl, LocalDateTime createdDate, String contents) {
            this.memberId = memberId;
            this.memberNickname = memberNickname;
            this.memberProfileUrl = memberProfileUrl;
            this.createdDate = createdDate;
            this.contents = contents;
        }
    }
}
