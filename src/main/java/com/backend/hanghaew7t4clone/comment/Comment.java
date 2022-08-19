package com.backend.hanghaew7t4clone.comment;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.likes.CardLike;
import com.backend.hanghaew7t4clone.likes.CommentLike;
import com.backend.hanghaew7t4clone.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "card_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    @Builder
    public Comment(Member member, String content, Card card) {
        this.member = member;
        this.content = content;
        this.card = card;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.ALL)
    private List<CommentLike> commentLikes = new ArrayList<>();

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }

    public void discountLike(CommentLike commentLike) {
        this.commentLikes.remove(commentLike);
    }
}
