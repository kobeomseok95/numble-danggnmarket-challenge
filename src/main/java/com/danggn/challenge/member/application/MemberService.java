package com.danggn.challenge.member.application;

import com.danggn.challenge.member.application.request.MemberJoinRequestVo;
import com.danggn.challenge.member.domain.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
class MemberService implements MemberUseCase {

    private final MemberJpaRepository memberJpaRepository;

    public void join(MemberJoinRequestVo memberJoinRequestVo) {
        memberJpaRepository.save(
                MemberApplicationAssembler.toMemberEntity(memberJoinRequestVo)
        );
    }
}
