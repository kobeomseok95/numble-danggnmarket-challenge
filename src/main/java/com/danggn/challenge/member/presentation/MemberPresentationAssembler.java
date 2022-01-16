package com.danggn.challenge.member.presentation;

import com.danggn.challenge.member.application.request.JoinMemberRequestVo;
import com.danggn.challenge.member.presentation.request.JoinMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MemberPresentationAssembler {

    private final PasswordEncoder passwordEncoder;

    public JoinMemberRequestVo toMemberJoinRequestVo(JoinMemberRequest joinMemberRequest) {
        return JoinMemberRequestVo.builder()
                .email(joinMemberRequest.getEmail())
                .password(passwordEncoder.encode(joinMemberRequest.getPassword()))
                .name(joinMemberRequest.getName())
                .phone(joinMemberRequest.getPhone())
                .nickname(joinMemberRequest.getNickname())
                .build();
    }
}
