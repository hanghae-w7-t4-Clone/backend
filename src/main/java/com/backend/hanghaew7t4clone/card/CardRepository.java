package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
  List<Card> findAllByMember(Member member);

  @Query("select distinct m from Card m join fetch m.member join fetch m.imgUrlList left join m.commentListDto order by m.createdAt DESC ")
  List<Card> findAllByOrderByCreatedAtDesc();

}