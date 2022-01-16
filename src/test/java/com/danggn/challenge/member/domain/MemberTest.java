package com.danggn.challenge.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    @DisplayName("회원 닉네임, 프로필 이미지 수정 / 성공")
    void updateInfo_success() throws Exception {

        // given
        Member target = Member.builder()
                .nickname("변경전")
                .build();
        Member source = Member.builder()
                .nickname("변경후")
                .profileImageUrl("변경후")
                .build();

        // when
        target.updateInfo(source);

        // then
        assertAll(
                () -> assertEquals(source.getNickname(), target.getNickname()),
                () -> assertEquals(source.getProfileImageUrl(), target.getProfileImageUrl())
        );
    }
}
