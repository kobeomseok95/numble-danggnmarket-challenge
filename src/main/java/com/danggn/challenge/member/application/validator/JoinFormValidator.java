package com.danggn.challenge.member.application.validator;

import com.danggn.challenge.member.domain.repository.MemberJpaRepository;
import com.danggn.challenge.member.presentation.request.JoinMemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinFormValidator implements Validator {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(JoinMemberRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinMemberRequest request = (JoinMemberRequest) target;
        if (memberJpaRepository.existsByEmail(request.getEmail())) {
            errors.rejectValue("email", "invalid email", "이미 존재하는 이메일입니다.");
        }
        if (memberJpaRepository.existsByNickname(request.getNickname())) {
            errors.rejectValue("nickname", "invalid nickname", "이미 존재하는 닉네임입니다.");
        }
        if (memberJpaRepository.existsByPhone(request.getPhone())) {
            errors.rejectValue("phone", "invalid phone", "이미 존재하는 휴대폰 번호입니다.");
        }
    }
}
