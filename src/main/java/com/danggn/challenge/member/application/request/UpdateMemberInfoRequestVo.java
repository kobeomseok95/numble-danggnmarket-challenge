package com.danggn.challenge.member.application.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@EqualsAndHashCode(of = "memberId")
@Builder
public class UpdateMemberInfoRequestVo {

    private final Long memberId;
    private final String nickname;
    private final MultipartFile profileFile;
}
