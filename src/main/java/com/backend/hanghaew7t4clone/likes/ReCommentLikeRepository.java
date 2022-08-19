//package com.backend.hanghaew7t4clone.likes;
//
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface ReCommentLikeRepository  extends JpaRepository<ReCommentLike, Long> {
//    Optional<ReCommentLike> findById(Long id);
//    Optional<ReCommentLike> findByReCommentAndMember(ReComment recomment, Member member);
//    List<ReCommentLike> findByReComment(ReComment reComment);
//
//    List<ReCommentLike> findReCommentLikesByMember(Member member);
//}
