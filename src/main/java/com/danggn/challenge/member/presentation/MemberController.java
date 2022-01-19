package com.danggn.challenge.member.presentation;

import com.danggn.challenge.common.security.AuthUser;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.member.application.MemberUseCase;
import com.danggn.challenge.member.application.validator.JoinFormValidator;
import com.danggn.challenge.member.application.validator.UpdateMemberNicknameValidator;
import com.danggn.challenge.member.presentation.request.JoinMemberRequest;
import com.danggn.challenge.member.presentation.request.LoginRequest;
import com.danggn.challenge.member.presentation.request.UpdateMemberInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberUseCase memberUseCase;
    private final MemberPresentationAssembler presentationAssembler;
    private final JoinFormValidator joinFormValidator;
    private final UpdateMemberNicknameValidator updateMemberNicknameValidator;

    @InitBinder("joinMemberRequest")
    public void initBinderJoinForm(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormValidator);
    }

    @InitBinder("updateMemberInfoRequest")
    public void initBinderUpdateMemberInfo(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateMemberNicknameValidator);
    }

    @GetMapping("/join")
    public String joinView(Model model) {
        model.addAttribute("joinMemberRequest", new JoinMemberRequest());
        return "member/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute("joinMemberRequest") JoinMemberRequest joinMemberRequest,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/joinForm";
        }

        memberUseCase.join(presentationAssembler.toMemberJoinRequestVo(joinMemberRequest));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginView(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "/member/loginForm";
    }

    @GetMapping("/me")
    public String myPageMain(Model model,
                             @AuthUser LoginMember loginMember) {
        model.addAttribute("me", presentationAssembler.toMyPageMainResponse(loginMember.getMember()));
        return "/member/myPage";
    }

    @GetMapping("/edit")
    public String editFormView(Model model,
                               @AuthUser LoginMember loginMember) {
        UpdateMemberInfoRequest request = presentationAssembler.toUpdateMemberInfoRequest(
                memberUseCase.findMemberInfoByMemberId(loginMember.getMemberId()));
        model.addAttribute("updateMemberInfoRequest", request);
        return "/member/editForm";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("updateMemberInfoRequest") UpdateMemberInfoRequest updateMemberInfoRequest,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "member/editForm";
        }

        memberUseCase.updateMemberInfo(presentationAssembler.toUpdateMemberInfoRequestVo(updateMemberInfoRequest));
        return "redirect:/members/me";
    }
}
