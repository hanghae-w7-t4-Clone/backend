package com.backend.hanghaew7t4clone.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberRequestDto {
    @NotBlank
    @Size(min = 4, max = 12)
    @Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
    private String nickname;

    @NotBlank
    @Size(min = 4, max = 32)
    @Pattern(regexp = "[a-zA-Z\\d]*${3,32}")
    private String password;

    @NotBlank
    private String passwordConfirm;

    @NotBlank
    private String gender;

    @NotBlank
    private String age;
}
