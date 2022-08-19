package com.backend.hanghaew7t4clone.likes;



import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardLikeRepository extends JpaRepository<CardLike, Long> {
    Optional<CardLike> findById(Long id);
    Optional<CardLike> findByCardAndMember(Card card, Member member);
    List<CardLike> findAllByCard(Card card);
    List<CardLike> findCardLikesByMember (Member member);
}
