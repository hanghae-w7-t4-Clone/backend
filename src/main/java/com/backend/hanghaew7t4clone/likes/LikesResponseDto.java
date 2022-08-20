package com.backend.hanghaew7t4clone.likes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikesResponseDto {
        private Long id;
        private Boolean likeClick;
        private String likeStatus;
    }
