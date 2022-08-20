package com.backend.hanghaew7t4clone.recomment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReCommentResponseDto {
    private Long id;
    private String profilePhoto;
    private String nickname;
    private String content;

    @Builder
    public ReCommentResponseDto(Long id, String profilePhoto, String nickname, String content) {
        this.id = id;
        this.profilePhoto = profilePhoto;
        this.nickname = nickname;
        this.content = content;
    }
}
