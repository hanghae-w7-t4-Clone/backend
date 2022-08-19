package com.backend.hanghaew7t4clone.member;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public LoginRequestDto(){};
    public LoginRequestDto(String loginId, String password){
        this.loginId = loginId;
        this.password =password;
    };
}