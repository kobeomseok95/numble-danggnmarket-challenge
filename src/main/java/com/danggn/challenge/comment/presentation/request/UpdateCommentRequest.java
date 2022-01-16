package com.danggn.challenge.comment.presentation.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCommentRequest {

    @NotNull(message = "수정할 댓글을 선택해주세요.")
    private Long commentId;

    @NotNull(message = "댓글을 수정할 상품을 선택해주세요.")
    private Long productId;

    @Length(min = 3, max = 100, message = "댓글 내용을 3 ~ 100자 사이로 입력해주세요.")
    private String contents;
}
