package com.backend.hanghaew7t4clone.domain.comment;

import com.backend.hanghaew7t4clone.domain.card.Card;
import com.backend.hanghaew7t4clone.domain.likes.Likes;
import com.backend.hanghaew7t4clone.domain.member.Member;
import com.backend.hanghaew7t4clone.domain.recomment.ReComment;
import com.backend.hanghaew7t4clone.web.comment.CommentResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column
    private int likeCount;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "card_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReComment> reCommentList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Likes> likesList;

    public Comment(String content, Member member, Card card) {
        this.content = content;
        this.member = member;
        this.card = card;
        this.likeCount = 0;
    }

    public CommentResponseDto getAllCommentDto() {
        return CommentResponseDto.builder()
                .id(this.id)
                .profilePhoto(this.getMember().getProfilePhoto())
                .nickname(this.getMember().getNickname())
                .content(this.content)
                .likeCount(this.likeCount)
                .build();
    }

    public void updateLikes(int likes) {
        this.likeCount= likes;
    }
    public void discountLikes(Likes likes) {
        this.likesList.remove(likes);
    }
}
