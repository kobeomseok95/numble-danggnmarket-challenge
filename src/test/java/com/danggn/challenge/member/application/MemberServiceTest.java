package com.danggn.challenge.member.application;

import com.danggn.challenge.common.manager.file.FileManager;
import com.danggn.challenge.member.application.request.JoinMemberRequestVo;
import com.danggn.challenge.member.application.request.UpdateMemberInfoRequestVo;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.member.domain.repository.MemberJpaRepository;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberJpaRepository memberJpaRepository;
    @Mock
    FileManager fileManager;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("회원 가입 / 성공")
    void joinTest_success() throws Exception {

        // given
        JoinMemberRequestVo request = createMockMemberJoinRequestVo();

        // when
        memberService.join(request);

        // then
        assertAll(
                () -> verify(memberJpaRepository).save(any(Member.class))
        );
    }

    private JoinMemberRequestVo createMockMemberJoinRequestVo() {
        return JoinMemberRequestVo.builder()
                .email("test@test.com")
                .password("12341234")
                .name("테스트")
                .phone("010-1234-1234")
                .nickname("테스트닉네임")
                .build();
    }

    @Test
    @DisplayName("회원 정보 수정 / 성공")
    void updateMemberInfoTest_success() throws Exception {

        // given
        Member member = createMember();
        UpdateMemberInfoRequestVo requestVo = createUpdateMemberInfoRequestVo(member);
        when(memberJpaRepository.findById(any()))
                .thenReturn(Optional.of(member));
        String mockUrl = "afterUrl";
        when(fileManager.uploadAndReturnStoredUrl(any()))
                .thenReturn(List.of(mockUrl));

        // when
        Long redirectMemberId = memberService.updateMemberInfo(requestVo);

        // then
        assertAll(
                () -> verify(memberJpaRepository).findById(member.getId()),
                () -> verify(fileManager).uploadAndReturnStoredUrl(List.of(requestVo.getProfileFile())),
                () -> assertEquals(requestVo.getNickname(), member.getNickname()),
                () -> assertEquals(mockUrl, member.getProfileImageUrl())
        );
    }

    private Member createMember() {
        return Member.builder()
                .email("test@test.com")
                .password("12341234")
                .name("테스트")
                .phone("010-1234-1234")
                .nickname("테스트닉네임")
                .profileImageUrl("testUrl")
                .build();
    }

    private UpdateMemberInfoRequestVo createUpdateMemberInfoRequestVo(Member member) {
        return UpdateMemberInfoRequestVo.builder()
                .memberId(member.getId())
                .nickname("변경닉네임")
                .profileFile(new MockMultipartFile("test1.jpg", "test1.jpg", ContentType.IMAGE_JPEG.getMimeType(), "test1.jpg".getBytes()))
                .build();
    }
}
