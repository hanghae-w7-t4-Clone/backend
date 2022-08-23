package com.backend.hanghaew7t4clone.member;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String nickname;
    private final String profilePhoto;


    public LoginResponseDto(String nickname, String profilePhoto){
        this.nickname=nickname;
        this.profilePhoto =profilePhoto;
    }
}
