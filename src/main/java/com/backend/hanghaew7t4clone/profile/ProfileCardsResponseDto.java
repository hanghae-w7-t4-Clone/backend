package com.backend.hanghaew7t4clone.profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCardsResponseDto {
   private Long id;
   private List<String> imgUrlList;
   private int likeCount;
   private int commentCount;
   private int photoCount;
}
