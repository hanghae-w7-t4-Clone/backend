package com.backend.hanghaew7t4clone.domain.card;

import com.backend.hanghaew7t4clone.domain.comment.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {
   private Long id;
   private String nickname;
   private Set<String> imgUrlList;
   private int likeCount;
   private String content;
   private int commentCount;
   private String place;
   private LocalDateTime createdAt;
   private LocalDateTime modifiedAt;
   private String profilePhoto;
   private List<CommentResponseDto> commentResponseDto;
}
