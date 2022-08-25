package com.backend.hanghaew7t4clone.web.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
   private Long id;
   private String nickname;
   private String name;
   private String content;
   private String profilePhoto;
   private int cardCount;
}
