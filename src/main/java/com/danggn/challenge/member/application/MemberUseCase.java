package com.danggn.challenge.member.application;

import com.danggn.challenge.member.application.request.JoinMemberRequestVo;
import com.danggn.challenge.member.application.request.UpdateMemberInfoRequestVo;
import com.danggn.challenge.member.application.response.MemberInfoResponseVo;

public interface MemberUseCase {

    void join(JoinMemberRequestVo joinMemberRequestVo);

    Long updateMemberInfo(UpdateMemberInfoRequestVo updateMemberInfoRequestVo);

    MemberInfoResponseVo findMemberInfoByMemberId(Long memberId);
}
