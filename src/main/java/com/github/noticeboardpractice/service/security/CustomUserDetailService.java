package com.github.noticeboardpractice.service.security;

import com.github.noticeboardpractice.repository.Roles.Roles;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipal;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipalRepository;
import com.github.noticeboardpractice.repository.memberPrincipal.MemberPrincipalRoles;
import com.github.noticeboardpractice.repository.userDetails.CustomUserDetails;
import com.github.noticeboardpractice.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberPrincipalRepository memberPrincipalRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberPrincipal memberPrincipal = memberPrincipalRepository.findByEmailFetchJoin(email)
                .orElseThrow(() -> new NotFoundException(email + "유저를 찾을 수 없습니다."));

        CustomUserDetails customUserDetails = CustomUserDetails
                .builder()
                .memberId(memberPrincipal.getMember()
                        .getMemberId())
                .email(memberPrincipal.getEmail())
                .password(memberPrincipal.getPassword())
                .authorities(
                        memberPrincipal.getMemberPrincipalRoles()
                                .stream()
                                .map(MemberPrincipalRoles::getRoles)
                                .map(Roles::getName)
                                .collect(Collectors.toList()))
                .build();

        return customUserDetails;
    }

}
