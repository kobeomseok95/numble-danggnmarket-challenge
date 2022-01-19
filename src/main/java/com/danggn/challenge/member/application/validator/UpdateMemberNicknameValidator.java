package com.danggn.challenge.member.application.validator;

import com.danggn.challenge.member.domain.repository.MemberJpaRepository;
import com.danggn.challenge.member.presentation.request.UpdateMemberInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UpdateMemberNicknameValidator implements Validator {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UpdateMemberInfoRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UpdateMemberInfoRequest request = (UpdateMemberInfoRequest) target;
        if (memberJpaRepository.existsByNickname(request.getNickname())) {
            errors.rejectValue("nickname", "invalid nickname", "이미 존재하는 닉네임입니다.");
        }
    }
}
