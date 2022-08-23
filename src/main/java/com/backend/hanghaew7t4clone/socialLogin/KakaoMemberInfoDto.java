package com.backend.hanghaew7t4clone.socialLogin;


import lombok.Getter;

@Getter
public class KakaoMemberInfoDto {
    private final String nickname;
    private final String name;
    private final String email;
    private final String profilePhoto;


    public KakaoMemberInfoDto(String email, String profilePhoto, String name, String nickname){
        this.name=name;
        this.email =email;
        this.profilePhoto=profilePhoto;
        this.nickname =nickname;
    }



}
