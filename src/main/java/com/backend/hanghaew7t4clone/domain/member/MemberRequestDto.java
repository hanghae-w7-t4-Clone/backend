package com.backend.hanghaew7t4clone.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {
    private String nickname;

    private String password;

    private String name;

    private String loginId;

}
