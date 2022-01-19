package com.danggn.challenge.member.application.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberInfoResponseVo {

    private final Long memberId;
    private final String nickname;
    private final String profileImageUrl;
}
