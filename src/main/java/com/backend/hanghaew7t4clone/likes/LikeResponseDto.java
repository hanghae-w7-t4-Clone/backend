package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.card.CardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {
    private List<CardResponseDto> cardList;
}
