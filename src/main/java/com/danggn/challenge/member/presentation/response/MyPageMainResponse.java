package com.danggn.challenge.member.presentation.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageMainResponse {

    private String profileImageUrl;
    private String nickname;
}
