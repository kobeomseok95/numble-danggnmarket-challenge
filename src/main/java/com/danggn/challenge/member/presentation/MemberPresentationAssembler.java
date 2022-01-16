package com.danggn.challenge.member.presentation;

import com.danggn.challenge.member.application.request.MemberJoinRequestVo;
import com.danggn.challenge.member.presentation.request.MemberJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MemberPresentationAssembler {

    private final PasswordEncoder passwordEncoder;

    public MemberJoinRequestVo toMemberJoinRequestVo(MemberJoinRequest memberJoinRequest) {
        return MemberJoinRequestVo.builder()
                .email(memberJoinRequest.getEmail())
                .password(passwordEncoder.encode(memberJoinRequest.getPassword()))
                .name(memberJoinRequest.getName())
                .phone(memberJoinRequest.getPhone())
                .nickname(memberJoinRequest.getNickname())
                .build();
    }
}
