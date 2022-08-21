package com.backend.hanghaew7t4clone.member;

import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String nickname;

    private String password;

    private String name;

    private String loginId;

    public MemberRequestDto(){}

    public MemberRequestDto(String nickname, String password, String name, String loginId){
        this.nickname=nickname;
        this.password=password;
        this.name=name;
        this.loginId=loginId;

    }
}
