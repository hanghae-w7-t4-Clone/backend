package com.backend.hanghaew7t4clone.web.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String profilePhoto;
    private String nickname;
    private String content;
    private int likeCount;

    @Builder
    public CommentResponseDto(Long id, String profilePhoto, String nickname, String content, int likeCount) {
        this.id = id;
        this.profilePhoto = profilePhoto;
        this.nickname = nickname;
        this.content = content;
        this.likeCount = likeCount;
    }

    @Builder
    public CommentResponseDto(String profilePhoto, String nickname, String content, int likeCount) {
        this.id = getId();
        this.profilePhoto = profilePhoto;
        this.nickname = nickname;
        this.content = content;
        this.likeCount = likeCount;
    }
}
