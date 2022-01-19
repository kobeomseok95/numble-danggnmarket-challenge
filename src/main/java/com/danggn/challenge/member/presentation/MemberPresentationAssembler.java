package com.danggn.challenge.member.presentation;

import com.danggn.challenge.member.application.request.JoinMemberRequestVo;
import com.danggn.challenge.member.application.request.UpdateMemberInfoRequestVo;
import com.danggn.challenge.member.application.response.MemberInfoResponseVo;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.member.presentation.request.JoinMemberRequest;
import com.danggn.challenge.member.presentation.request.UpdateMemberInfoRequest;
import com.danggn.challenge.member.presentation.response.MyPageMainResponse;
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

    public MyPageMainResponse toMyPageMainResponse(Member member) {
        return MyPageMainResponse.builder()
                .nickname(member.getNickname())
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

    public UpdateMemberInfoRequest toUpdateMemberInfoRequest(MemberInfoResponseVo member) {
        return UpdateMemberInfoRequest.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileFile(null)
                .profileImageUrl(member.getProfileImageUrl())
                .build();
    }

    public UpdateMemberInfoRequestVo toUpdateMemberInfoRequestVo(UpdateMemberInfoRequest updateMemberInfoRequest) {
        return UpdateMemberInfoRequestVo.builder()
                .memberId(updateMemberInfoRequest.getMemberId())
                .nickname(updateMemberInfoRequest.getNickname())
                .profileFile(updateMemberInfoRequest.getProfileFile())
                .build();
    }
}
