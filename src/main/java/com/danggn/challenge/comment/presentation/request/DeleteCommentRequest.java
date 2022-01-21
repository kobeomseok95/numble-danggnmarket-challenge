package com.danggn.challenge.comment.presentation.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteCommentRequest {

    private Long productId;
}
