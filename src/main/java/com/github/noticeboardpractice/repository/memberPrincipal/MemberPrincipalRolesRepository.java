package com.github.noticeboardpractice.repository.memberPrincipal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPrincipalRolesRepository extends JpaRepository<MemberPrincipalRoles, Long> {
}
