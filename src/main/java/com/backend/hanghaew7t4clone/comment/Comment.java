package com.backend.hanghaew7t4clone.comment;

import com.backend.hanghaew7t4clone.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

//    @JoinColumn(name = "card_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Card card;

    @Builder
    public Comment(Long id, String content, Member member, Card card) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.card = card;
    }
}
