package com.backend.hanghaew7t4clone.domain.member;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByPhoneNum(String phoneNum);
    List<Member> findTop5ByOrderByCreatedAtDesc();
}