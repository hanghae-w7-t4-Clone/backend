package com.backend.hanghaew7t4clone.domain.likes;


import com.backend.hanghaew7t4clone.domain.card.Card;
import com.backend.hanghaew7t4clone.domain.comment.Comment;
import com.backend.hanghaew7t4clone.domain.member.Member;
import com.backend.hanghaew7t4clone.domain.recomment.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Query("select DISTINCT m from Likes m where (m.card in :card) And (m.member in :member)")
    Optional<Likes> findByCardAndMember(@Param("card") Card card, @Param("member") Member member);

    @Query("select DISTINCT m from Likes m where (m.comment in :comment) And (m.member in :member)")
    Optional<Likes> findByCommentAndMember(@Param("comment") Comment comment,@Param("member") Member member);

    @Query("select DISTINCT m from Likes m where (m.reComment in :reComment) And (m.member in :member)")
    Optional<Likes> findByReCommentAndMember(@Param("reComment") ReComment reComment,@Param("member") Member member);

    List<Likes> findAllByCard(Card card);

    List<Likes> findAllByComment(Comment comment);

    List<Likes> findAllByReComment(ReComment reComment);
}
