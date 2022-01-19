package com.danggn.challenge.member.application;

import com.danggn.challenge.common.manager.file.FileManager;
import com.danggn.challenge.member.application.request.JoinMemberRequestVo;
import com.danggn.challenge.member.application.request.UpdateMemberInfoRequestVo;
import com.danggn.challenge.member.application.response.MemberInfoResponseVo;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.member.domain.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class MemberService implements MemberUseCase {

    private final MemberJpaRepository memberJpaRepository;
    private final FileManager fileManager;

    public void join(JoinMemberRequestVo joinMemberRequestVo) {
        memberJpaRepository.save(MemberApplicationAssembler.toMemberEntity(joinMemberRequestVo));
    }

    @Override
    public Long updateMemberInfo(UpdateMemberInfoRequestVo updateMemberInfoRequestVo) {
        String profileUrl = fileManager.uploadAndReturnStoredUrl(List.of(updateMemberInfoRequestVo.getProfileFile()))
                .get(0);
        Member target = memberJpaRepository.findById(updateMemberInfoRequestVo.getMemberId())
                .orElseThrow();
        Member source = MemberApplicationAssembler.toMemberEntity(updateMemberInfoRequestVo, profileUrl);
        target.updateInfo(source);
        return target.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public MemberInfoResponseVo findMemberInfoByMemberId(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow();
        return MemberApplicationAssembler.toMemberInfoResponseVo(member);
    }
}
