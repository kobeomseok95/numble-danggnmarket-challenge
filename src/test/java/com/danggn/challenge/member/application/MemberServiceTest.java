package com.danggn.challenge.member.application;

import com.danggn.challenge.member.application.request.MemberJoinRequestVo;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.member.domain.repository.MemberJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberJpaRepository memberJpaRepository;
    @Mock
    MemberApplicationAssembler memberApplicationAssembler;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("회원 가입 / 성공")
    void joinTest_success() throws Exception {

        // given
        MemberJoinRequestVo request = createMockMemberJoinRequestVo();
        when(memberApplicationAssembler.toMemberEntity(request))
                .thenReturn(createMockMember());

        // when
        memberService.join(request);

        // then
        assertAll(
                () -> verify(memberJpaRepository).save(any(Member.class)),
                () -> verify(memberApplicationAssembler).toMemberEntity(request)
        );
    }

    private MemberJoinRequestVo createMockMemberJoinRequestVo() {
        return MemberJoinRequestVo.builder()
                .email("test@test.com")
                .password("12341234")
                .name("테스트")
                .phone("010-1234-1234")
                .nickname("테스트닉네임")
                .build();
    }

    private Member createMockMember() {
        return Member.builder()
                .email("test@test.com")
                .password("12341234")
                .name("테스트")
                .phone("010-1234-1234")
                .nickname("테스트닉네임")
                .build();
    }
}