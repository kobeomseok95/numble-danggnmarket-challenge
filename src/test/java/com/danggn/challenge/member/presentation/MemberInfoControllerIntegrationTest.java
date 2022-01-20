package com.danggn.challenge.member.presentation;

import com.danggn.challenge.IntegrationTest;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.member.domain.repository.MemberJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MemberInfoControllerIntegrationTest extends IntegrationTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입 뷰 / 성공")
    void joinViewTest_success() throws Exception {

        // given, when, then
        mockMvc.perform(get("/members/join"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberInfoController.class))
                .andExpect(handler().methodName("joinView"))
                .andExpect(view().name("member/joinForm"))
                .andExpect(model().attributeExists("joinMemberRequest"));
    }

    @Test
    @DisplayName("회원 가입 / 성공")
    void joinTest_success() throws Exception {

        // given, when, then
        mockMvc.perform(post("/members/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "a@a.com")
                .param("password", "1234")
                .param("name", "고범석")
                .param("phone", "01012341234")
                .param("nickname", "고범석"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(handler().handlerType(MemberInfoController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("회원 가입 / 실패 - 모든 값을 입력하지 않은 경우")
    void joinTest_fail_shouldNotBlank() throws Exception {

        // given, when, then
        mockMvc.perform(post("/members/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "")
                .param("password", "")
                .param("name", "")
                .param("phone", "")
                .param("nickname", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberInfoController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(model().errorCount(7));
    }

    @Test
    @DisplayName("회원 가입 / 실패 - 이메일 형식, 성함 최소 글자, 휴대전화 형식을 지키지 않은 경우")
    void joinTest_fail_shouldRequireEmailNamePhonePattern() throws Exception {

        // given, when, then
        mockMvc.perform(post("/members/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", "avx")
                .param("password", "1234")
                .param("name", "김")
                .param("phone", "0104")
                .param("nickname", "김"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberInfoController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(model().errorCount(3));
    }

    @Test
    @DisplayName("회원 가입 / 실패 - 중복된 이메일, 닉네임, 휴대전화가 있을 경우")
    void joinTest_fail_shouldNotDuplicateEmailNicknamePhone() throws Exception {

        // given
        final String email = "a@a.com";
        final String nickname = "nick";
        final String phone = "01012341234";
        saveMember(email, nickname, phone);

        // when, then
        mockMvc.perform(post("/members/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", email)
                .param("password", "1234")
                .param("name", "테스트")
                .param("phone", phone)
                .param("nickname", nickname))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberInfoController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(model().errorCount(3));
    }

    private void saveMember(String email, String nickname, String phone) {
        memberJpaRepository.save(Member.builder()
                .email(email)
                .nickname(nickname)
                .phone(phone)
                .name("테스트")
                .password(passwordEncoder.encode("1234"))
                .build());
    }

    @Test
    @DisplayName("로그인 뷰 / 성공")
    void loginView_success() throws Exception {

        // given, when, then
        mockMvc.perform(get("/members/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberInfoController.class))
                .andExpect(handler().methodName("loginView"))
                .andExpect(view().name("/member/loginForm"))
                .andExpect(model().attributeExists("loginRequest"));
    }

    @Test
    @DisplayName("로그인 / 성공")
    void login_success() throws Exception {

        // given
        final String email = "a@b.com";
        final String nickname = "테스트닉네임";
        final String phone = "01012341234";
        saveMember(email, nickname, phone);

        // when, then
        mockMvc.perform(post("/members/login")
                .with(csrf())
                .param("email", email)
                .param("password", "1234"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products"));
    }

    @Test
    @DisplayName("로그인 / 실패 - 정보가 일치하지 않는 경우")
    void login_fail_shouldRightEmailPassword() throws Exception {

        // given
        final String email = "a@b.com";
        final String nickname = "테스트닉네임";
        final String phone = "01012341234";
        saveMember(email, nickname, phone);

        // when, then
        mockMvc.perform(post("/members/login")
                .with(csrf())
                .param("email", email)
                .param("password", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members/login?error"));
    }

    @Test
    @DisplayName("로그인 / 실패 - 없는 회원일 경우")
    void login_fail_notFoundMember() throws Exception {

        // given, when, then
        mockMvc.perform(post("/members/login")
                .with(csrf())
                .param("email", "a@b.com")
                .param("password", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/members/login?error"));
    }

    @Test
    @DisplayName("로그아웃 테스트 - 성공")
    void logout_success() throws Exception {

        // given, when, then
        mockMvc.perform(post("/members/logout")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }
}
