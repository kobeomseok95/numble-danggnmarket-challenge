package com.danggn.challenge.member.application.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(of = {"email", "phone", "nickname"})
@Builder
public class JoinMemberRequestVo {

    private final String email;
    private final String password;
    private final String name;
    private final String phone;
    private final String nickname;
}
