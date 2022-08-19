package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository  extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findById(Long id);
    Optional<CommentLike>findByCommentAndMember(CardLike comment, Member member);

    List<CommentLike> findByComment(Comment comment);

    List<CommentLike> findCommentLikesByMember(Member member);
}
