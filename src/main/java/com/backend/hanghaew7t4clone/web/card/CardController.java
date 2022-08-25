package com.backend.hanghaew7t4clone.web.card;

import com.backend.hanghaew7t4clone.domain.card.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CardController {
  private final CardService cardService;

  @RequestMapping(value = "/auth/cards", method = RequestMethod.POST)
  public ResponseEntity<?> createCard(@RequestBody CardRequestDto requestDto,
                                      HttpServletRequest request) {
    return cardService.createCard(requestDto, request);
  }
  @RequestMapping(value = "/cards/{id}", method = RequestMethod.GET)
  public ResponseEntity<?> getCard(@PathVariable Long id) {
    return cardService.getCard(id);
  }
  @RequestMapping(value = "/cards", method = RequestMethod.GET)
  public ResponseEntity<?> getAllCards() {
    return cardService.getAllCard();
  }

  @RequestMapping(value = "/auth/cards/{id}", method = RequestMethod.PATCH)
  public ResponseEntity<?> updateCard(@PathVariable Long id, @RequestBody CardRequestDto cardRequestDto,
      HttpServletRequest request) {
    return cardService.updateCard(id, cardRequestDto, request);
  }
  @RequestMapping(value = "/auth/cards/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> deleteCard(@PathVariable Long id,
      HttpServletRequest request) {
    return cardService.deleteCard(id, request);
  }
}