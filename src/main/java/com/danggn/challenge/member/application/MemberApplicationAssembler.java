package com.danggn.challenge.member.application;

import com.danggn.challenge.member.application.request.JoinMemberRequestVo;
import com.danggn.challenge.member.application.request.UpdateMemberInfoRequestVo;
import com.danggn.challenge.member.application.response.MemberInfoResponseVo;
import com.danggn.challenge.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
class MemberApplicationAssembler {

    static Member toMemberEntity(JoinMemberRequestVo joinMemberRequestVo) {
        return Member.builder()
                .email(joinMemberRequestVo.getEmail())
                .password(joinMemberRequestVo.getPassword())
                .name(joinMemberRequestVo.getName())
                .phone(joinMemberRequestVo.getPhone())
                .nickname(joinMemberRequestVo.getNickname())
                .build();
    }

    static Member toMemberEntity(UpdateMemberInfoRequestVo updateMemberInfoRequestVo, String profileUrl) {
        return Member.builder()
                .nickname(updateMemberInfoRequestVo.getNickname())
                .profileImageUrl(profileUrl)
                .build();
    }

    static MemberInfoResponseVo toMemberInfoResponseVo(Member member) {
        return MemberInfoResponseVo.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }
}
