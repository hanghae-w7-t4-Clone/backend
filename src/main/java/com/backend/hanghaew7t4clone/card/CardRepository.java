package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
  List<Card> findAllByMember(Member member);

  @Query("select m from Card m join fetch m.member order by m.createdAt DESC ")
  List<Card> findAllByOrderByCreatedAtDesc();

  @Query("select m from Card m join fetch m.commentListDto order by m.createdAt DESC ")
  List<Card> findAllByOrderByCreatedAt();

  @Query("select m from Card m join fetch m.imgUrlList order by m.createdAt DESC ")
  List<Card> findAllByOrderByModifiedAt();


}