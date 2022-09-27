package com.backend.hanghaew7t4clone.domain.recomment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReCommentResponseDto {
    private Long id;
    private String nickname;
    private String content;
    private String profilePhoto;


    @Builder
    public ReCommentResponseDto(Long id, String nickname, String content, String profilePhoto) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.profilePhoto = profilePhoto;
    }
}
