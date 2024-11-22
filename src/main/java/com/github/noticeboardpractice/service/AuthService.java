package com.github.noticeboardpractice.service;

import com.github.noticeboardpractice.config.security.JwtTokenProvider;
import com.github.noticeboardpractice.repository.member.Member;
import com.github.noticeboardpractice.repository.member.MemberRepository;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipal;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipalRepository;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipalRoles;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipalRolesRepository;
import com.github.noticeboardpractice.repository.roles.Roles;
import com.github.noticeboardpractice.repository.roles.RolesRepository;
import com.github.noticeboardpractice.service.exceptions.NotAcceptException;
import com.github.noticeboardpractice.service.exceptions.NotFoundException;
import com.github.noticeboardpractice.web.dto.auth.Login;
import com.github.noticeboardpractice.web.dto.auth.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final MemberRepository memberRepository;
    private final MemberPrincipalRepository memberPrincipalRepository;
    private final MemberPrincipalRolesRepository memberPrincipalRolesRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional(transactionManager = "tmJpa1")
    public boolean SignUp(SignUp signUpRequest) {

        Roles role;

        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();
        String name = signUpRequest.getName();
        LocalDateTime now = LocalDateTime.now();

        if (memberPrincipalRepository.existsByEmail(email)) {
            return false;
        }

//        if (memberPrincipalRepository.existsByRoles)

        Member member = Member.builder()
                .name(name)
                .createdAt(now)
                .build();

        if (name.equalsIgnoreCase("ADMIN")) {
            role = rolesRepository.findByName("ADMIN");
        } else {
            role = rolesRepository.findByName("USER");
        }

        MemberPrincipal memberPrincipal = MemberPrincipal.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .member(member)
                .build();

        MemberPrincipalRoles memberPrincipalRoles = MemberPrincipalRoles.builder()
                .memberPrincipal(memberPrincipal)
                .roles(role)
                .build();

        memberRepository.save(member);
        memberPrincipalRepository.save(memberPrincipal);
        memberPrincipalRolesRepository.save(memberPrincipalRoles);

        return true;

    }

    public String login(Login loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            MemberPrincipal memberPrincipal = memberPrincipalRepository.findByEmailFetchJoin(email)
                    .orElseThrow( () -> new NotFoundException("email 을 찾을 수 없습니다.") );

            List<String> roles = memberPrincipal.getMemberPrincipalRoles()
                    .stream()
                    .map(MemberPrincipalRoles::getRoles)
                    .map(Roles::getName)
                    .toList();

            return jwtTokenProvider.createToken(email, roles);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotAcceptException("로그인을 할 수 없습니다.");
        }

    }
}
