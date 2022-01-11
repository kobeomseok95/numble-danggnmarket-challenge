package com.danggn.challenge.member.application;

import com.danggn.challenge.member.application.request.MemberJoinRequestVo;

public interface MemberUseCase {

    void join(MemberJoinRequestVo memberJoinRequestVo);
}
