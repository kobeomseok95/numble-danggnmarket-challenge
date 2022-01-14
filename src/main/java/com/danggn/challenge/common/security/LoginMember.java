package com.danggn.challenge.common.security;

import com.danggn.challenge.member.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class LoginMember extends User {

    private Member member;

    public LoginMember(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
    }
}
