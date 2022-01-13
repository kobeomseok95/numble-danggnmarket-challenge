package com.danggn.challenge.member.presentation;

import com.danggn.challenge.member.application.MemberUseCase;
import com.danggn.challenge.member.application.validator.JoinFormValidator;
import com.danggn.challenge.member.presentation.request.LoginRequest;
import com.danggn.challenge.member.presentation.request.MemberJoinRequest;
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

    @InitBinder("memberJoinRequest")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(joinFormValidator);
    }

    @GetMapping("/join")
    public String joinView(Model model) {
        model.addAttribute("memberJoinRequest", new MemberJoinRequest());
        return "member/joinForm";
    }

    @PostMapping("/join")
    public String join(
            @Valid @ModelAttribute("memberJoinRequest") MemberJoinRequest memberJoinRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "member/joinForm";
        }

        memberUseCase.join(presentationAssembler.toMemberJoinRequestVo(memberJoinRequest));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginView(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "/member/loginForm";
    }
}
