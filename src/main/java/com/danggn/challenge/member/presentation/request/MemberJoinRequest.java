package com.danggn.challenge.member.presentation.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberJoinRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "성함을 입력해주세요.")
    @Length(min = 2, max = 10, message = "성함은 최소 2자 이상 10자 이하로 입력해주세요.")
    private String name;

    @NotBlank(message = "휴대전화 번호를 입력해주세요.'-' 없이 입력해주세요")
    @Pattern(
            regexp = "\\d{11}",
            message = "휴대전화 형식으로 입력해주세요."
    )
    private String phone;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
