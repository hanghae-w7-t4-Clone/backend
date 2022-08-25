package com.backend.hanghaew7t4clone.domain.member.socialLogin;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoMemberInfoDto {
    private final String nickname;
    private final String name;
    private final String email;
    private final String profilePhoto;


}
