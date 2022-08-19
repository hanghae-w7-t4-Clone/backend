package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
  List<Card> findAllByMember(Member member);
  List<Card> findAllByOrderbyCreatedAtDesc();
}