package com.danggn.challenge.member.application;

import com.danggn.challenge.member.application.request.MemberJoinRequestVo;
import com.danggn.challenge.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
class MemberApplicationAssembler {

    static Member toMemberEntity(MemberJoinRequestVo memberJoinRequestVo) {
        return Member.builder()
                .email(memberJoinRequestVo.getEmail())
                .password(memberJoinRequestVo.getPassword())
                .name(memberJoinRequestVo.getName())
                .phone(memberJoinRequestVo.getPhone())
                .nickname(memberJoinRequestVo.getNickname())
                .build();
    }
}
