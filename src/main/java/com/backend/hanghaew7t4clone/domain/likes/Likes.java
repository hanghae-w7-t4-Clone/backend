package com.backend.hanghaew7t4clone.domain.likes;


import com.backend.hanghaew7t4clone.domain.card.Card;
import com.backend.hanghaew7t4clone.domain.comment.Comment;
import com.backend.hanghaew7t4clone.domain.member.Member;
import com.backend.hanghaew7t4clone.domain.recomment.ReComment;
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
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "re_comment_id")
    private ReComment reComment;

    @Builder
    public Likes (Member member, Card card){
        this.member=member;
        this.card=card;
        this.comment=null;
    }

    @Builder
    public Likes (Member member, Comment comment){
        this.member=member;
        this.card=null;
        this.comment=comment;
    }

    @Builder
    public Likes (Member member, ReComment reComment){
        this.member=member;
        this.card=null;
        this.reComment=reComment;
    }


}
