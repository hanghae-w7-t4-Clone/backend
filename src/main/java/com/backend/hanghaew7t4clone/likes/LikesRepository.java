package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findById(Long id);

    @Query("select DISTINCT m from Likes m where (m.card in :card) And (m.member in :member)")
    Optional<Likes> findByCardAndMember(@Param("card") Card card, @Param("member") Member member);

    @Query("select DISTINCT m from Likes m where (m.comment in :comment) And (m.member in :member)")
    Optional<Likes> findByCommentAndMember(Comment comment, Member member);

}
