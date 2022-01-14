package com.danggn.challenge.common.security;


import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.member.domain.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthMemberDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberJpaRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디와 비밀번호를 확인해주세요."));

        return new LoginMember(member);
    }
}
