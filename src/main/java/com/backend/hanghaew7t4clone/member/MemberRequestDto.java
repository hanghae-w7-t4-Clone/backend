package com.backend.hanghaew7t4clone.member;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class MemberRequestDto {
    private String nickname;

    private String password;

    private String name;

    @Nullable
    private String email;

    @Nullable
    private String phoneNum;

    public MemberRequestDto(){};

    public MemberRequestDto(String nickname, String password, String name, String email, String phoneNum){
        this.nickname=nickname;
        this.password=password;
        this.name=name;
        this.email=email;
        this.phoneNum=phoneNum;

    }
}
