package com.danggn.challenge.member.presentation.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMemberInfoRequest {

    @NotNull(message = "회원의 ID를 입력해주세요.")
    private Long memberId;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    private MultipartFile profileFile;

    private String profileImageUrl;
}
