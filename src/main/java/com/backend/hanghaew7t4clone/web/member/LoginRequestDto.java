package com.backend.hanghaew7t4clone.web.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

}