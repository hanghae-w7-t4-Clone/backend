package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
  @Query("select distinct m from Card m join fetch m.imgUrlList where m.member in :member order by m.createdAt DESC ")
  List<Card> findAllByMember(@Param ("member") Member member);
  @Query("select distinct m from Card m join fetch m.member join fetch m.imgUrlList left join fetch m.commentListDto order by m.createdAt DESC ")
  List<Card> findAllByOrderByCreatedAtDesc();

}