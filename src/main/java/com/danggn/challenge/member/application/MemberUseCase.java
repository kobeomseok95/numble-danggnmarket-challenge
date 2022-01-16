package com.danggn.challenge.member.application;

import com.danggn.challenge.member.application.request.JoinMemberRequestVo;
import com.danggn.challenge.member.application.request.UpdateMemberInfoRequestVo;

public interface MemberUseCase {

    void join(JoinMemberRequestVo joinMemberRequestVo);

    Long updateMemberInfo(UpdateMemberInfoRequestVo updateMemberInfoRequestVo);
}
