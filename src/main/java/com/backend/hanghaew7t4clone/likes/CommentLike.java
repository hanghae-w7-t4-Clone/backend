package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comments_id")
    private Comment comment;

    @Builder
    public CommentLike(Member member,Comment comment){
        this.comment=comment;
        this.member=member;
    }


}
