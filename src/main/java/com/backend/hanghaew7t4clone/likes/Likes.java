package com.backend.hanghaew7t4clone.Likess;


import com.backend.hanghaew7t4clone.card.Card;
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

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "sub_comment_id")
//    private SubComment subComment;



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


}
