package com.backend.hanghaew7t4clone.web.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileMembersResponseDto {
    private Long id;
    private String profilePhoto;
    private String nickname;

    @Builder
    public ProfileMembersResponseDto(Long id, String profilePhoto, String nickname) {
        this.id = id;
        this.profilePhoto = profilePhoto;
        this.nickname = nickname;
    }
}
