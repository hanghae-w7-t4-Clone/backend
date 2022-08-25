package com.backend.hanghaew7t4clone.recomment;

import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.likes.Likes;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.shared.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ReComment extends Timestamped {

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

    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reComment", cascade = CascadeType.ALL)
    private List<Likes> likesList;

    public ReComment(String content, Member member, Comment comment) {
        this.content = content;
        this.member = member;
        this.comment = comment;
        this.likeCount = 0;
    }

    public ReCommentResponseDto getAllReCommentDto() {
        return ReCommentResponseDto.builder()
                .id(this.id)
                .profilePhoto(this.getMember().getProfilePhoto())
                .nickname(this.getMember().getNickname())
                .content(this.content)
                .build();
    }

    public void updateLikes(int likes) {
        this.likeCount= likes;
    }
    public void discountLikes(Likes likes) {
        this.likesList.remove(likes);
    }

}
