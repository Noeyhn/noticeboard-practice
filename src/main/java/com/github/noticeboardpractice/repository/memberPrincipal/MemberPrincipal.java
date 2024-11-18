package com.github.noticeboardpractice.repository.memberPrincipal;

import com.github.noticeboardpractice.repository.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "member_principal")
public class MemberPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_principal_id")
    private Long memberPrincipalId;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "memberPrincipal")
    private Collection<MemberPrincipalRoles> memberPrincipalRoles;

}
