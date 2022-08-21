package com.backend.hanghaew7t4clone.socialLogin;


import com.backend.hanghaew7t4clone.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginResponseDto {

    private Long MemberId;
    private String nickname;
    private int myPoint;
    private int lottoCount;

    public SocialLoginResponseDto(Member kakaoMember) {
        this.MemberId = kakaoMember.getId();
        this.nickname = kakaoMember.getNickname();
    }
}
