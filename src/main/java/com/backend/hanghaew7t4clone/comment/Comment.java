package com.backend.hanghaew7t4clone.comment;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.likes.Likes;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.recomment.ReComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReComment> reCommentList;

    @Column
    private int likeCount;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Likes> likesList;

    public Comment(String content, Member member, Card card) {
        this.content = content;
        this.member = member;
        this.card = card;
    }

    public CommentResponseDto getAllCommentDto() {
        return CommentResponseDto.builder()
                .id(this.id)
                .profilePhoto(this.getMember().getProfilePhoto())
                .content(this.content)
                .nickname(this.getMember().getNickname())
                .build();

    }

    public void updateLikes() {
        this.likeCount= likesList.size();
    }
    public void discountLikes(Likes likes) {
        this.likesList.remove(likes);
    }
}
