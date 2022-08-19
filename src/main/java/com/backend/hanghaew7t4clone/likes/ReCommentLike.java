//package com.backend.hanghaew7t4clone.likes;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//
//@Entity
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class ReCommentLike {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "re_comment_id")
//    private ReComment reComment;
//
//}
