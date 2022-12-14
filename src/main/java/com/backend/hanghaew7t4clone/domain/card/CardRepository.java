package com.backend.hanghaew7t4clone.domain.card;

import com.backend.hanghaew7t4clone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
  List<Card> findAllByMember( Member member);
  @Query("select distinct m from Card m join fetch m.member join fetch m.imgUrlList left join fetch m.commentListDto order by m.createdAt DESC ")
  List<Card> findAllByOrderByCreatedAtDesc();

}