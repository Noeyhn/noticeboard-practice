package com.github.noticeboardpractice.repository.memberPrincipal;

import com.github.noticeboardpractice.repository.roles.Roles;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "member_principal_roles")
public class MemberPrincipalRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_principal_role_id")
    private Long memberPrincipalRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_principal_id")
    private MemberPrincipal memberPrincipal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Roles roles;

}
