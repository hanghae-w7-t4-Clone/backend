package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findById(Long id);
    Optional<Likes> findByCardAndMember(Card card, Member member);
    List<Likes> findAllByCard(Card card);
    List<Likes> findCardLikesByMember (Member member);

    Optional<Likes> findByCommentAndMember(Comment comment, Member member);
    List<Likes> findByComment(Comment comment);
    List<Likes> findCommentLikesByMember(Member member);
}
