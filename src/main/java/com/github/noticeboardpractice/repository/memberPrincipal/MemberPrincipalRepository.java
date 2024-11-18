package com.github.noticeboardpractice.repository.memberPrincipal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberPrincipalRepository extends JpaRepository<MemberPrincipal, Long> {

    @Query("SELECT mp FROM MemberPrincipal mp JOIN FETCH mp.memberPrincipalRoles mpr JOIN FETCH mpr.roles WHERE mp.email = :email")
    Optional<MemberPrincipal> findByEmailFetchJoin(String email);

    boolean existsByEmail(String email);
}
