package com.backend.hanghaew7t4clone.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardRequestDto {
  private String content;
  private List<String> imgUrlList;
}