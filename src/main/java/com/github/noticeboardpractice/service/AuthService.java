package com.github.noticeboardpractice.service;

import com.github.noticeboardpractice.repository.member.Member;
import com.github.noticeboardpractice.repository.member.MemberRepository;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipal;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipalRepository;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipalRolesRepository;
import com.github.noticeboardpractice.web.dto.auth.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final MemberPrincipalRepository memberPrincipalRepository;
    private final MemberPrincipalRolesRepository memberPrincipalRolesRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean SignUp(SignUp signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String name = signUpRequest.getName();
        LocalDateTime now = LocalDateTime.now();

        if (memberPrincipalRepository.existsByEmail(email)) {
            return false;
        }

        Member member = Member.builder()
                .name(name)
                .createdAt(now)
                .build();

        MemberPrincipal memberPrincipal = MemberPrincipal.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        memberRepository.save(member);
        memberPrincipalRepository.save(memberPrincipal);

        return true;
    }
}
