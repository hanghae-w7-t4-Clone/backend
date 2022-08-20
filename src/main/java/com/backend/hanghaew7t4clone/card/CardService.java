package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.exception.*;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

   private final CardRepository cardRepository;
   private final TokenProvider tokenProvider;
   private final CommentRepository commentRepository;


   @Transactional
   public ResponseEntity<?> createCard(CardRequestDto requestDto, HttpServletRequest request) {

      Member member = validateMember(request);
      tokenCheck(request, member);

      Card card = Card.builder()
              .nickname(member.getNickname())
              .imgUrlList(requestDto.getImgUrlList())
              .likeCount(0)
              .content(requestDto.getContent())
              .commentCount(0)
              .place(requestDto.getPlace())
              .member(member)
              .build();
      cardRepository.save(card);
      return new ResponseEntity<>(Message.success(
              CardResponseDto.builder()
                      .id(card.getId())
                      .nickname(card.getMember().getNickname())
                      .imgUrlList(card.getImgUrlList())
                      .likeCount(card.getLikeCount())
                      .content(card.getContent())
                      .commentCount(card.getCommentCount())
                      .place(card.getPlace())
                      .createdAt(card.getCreatedAt())
                      .modifiedAt(card.getModifiedAt())
                      .build()
      ), HttpStatus.OK);
   }

   @Transactional(readOnly = true)
   public ResponseEntity<?> getCard(Long id) {
      Card card = isPresentCard(id);
      if (null == card) {
         throw new CustomException(ErrorCode.CARD_NOT_FOUND);
      }

      return new ResponseEntity<>(Message.success(
              CardResponseDto.builder()
                      .id(card.getId())
                      .nickname(card.getMember().getNickname())
                      .imgUrlList(card.getImgUrlList())
                      .likeCount(card.getLikeCount())
                      .content(card.getContent())
                      .commentCount(card.getCommentCount())
                      .place(card.getPlace())
                      .createdAt(card.getCreatedAt())
                      .build()
      ),HttpStatus.OK);
   }

   @Transactional(readOnly = true)
   public ResponseEntity<?> getAllCard() {
      return new ResponseEntity<>(Message.success(cardRepository.findAllByOrderByCreatedAtDesc())
              ,HttpStatus.OK);
   }

   @Transactional
   public ResponseEntity<?> updateCard(Long id, CardRequestDto requestDto, HttpServletRequest request) {
      Member member = validateMember(request);
      Card card = isPresentCard(id);
      tokenCheck(request,member);
      cardCheck(member, card);
      card.update(requestDto);
      return new ResponseEntity<>(Message.success(card),HttpStatus.OK);
   }

   @Transactional
   public ResponseEntity<?> deleteCard(Long id, HttpServletRequest request) {

      Member member = validateMember(request);
      Card card = isPresentCard(id);
      tokenCheck(request, member);
      cardCheck(member, card);
      //cascade 안먹을때는 아래 거로 쓰기
      List<Comment> commentList = commentRepository.findAllByCard(card);
      for (Comment comment : commentList) {
         commentRepository.delete(comment);
      }
      cardRepository.delete(card);
      return new ResponseEntity<>(Message.success("delete success"),HttpStatus.OK);
   }

   private void cardCheck(Member member, Card card) {
      if (null == card) {
         throw new CustomException(ErrorCode.CARD_NOT_FOUND);
      }
      if (card.getMember().getId()!= member.getId()) {
         throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
      }
   }

   private void tokenCheck(HttpServletRequest request, Member member) {
      if (null == request.getHeader("Refresh-Token")) {
         throw new CustomException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
      }
      if (null == request.getHeader("Authorization")) {
         throw new CustomException(ErrorCode.TOKEN_IS_EXPIRED);
      }
      if (null == member) {
         throw new CustomException(ErrorCode.AUTHOR_NOT_FOUND);
      }
   }
   @Transactional(readOnly = true)
   public Card isPresentCard(Long id) {
      Optional<Card> optionalCard = cardRepository.findById(id);
      return optionalCard.orElse(null);
   }

   @Transactional
   public Member validateMember(HttpServletRequest request) {
      if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
         return null;
      }
      return tokenProvider.getMemberFromAuthentication();
   }
}
