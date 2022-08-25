package com.backend.hanghaew7t4clone.domain.card;

import com.backend.hanghaew7t4clone.domain.comment.Comment;
import com.backend.hanghaew7t4clone.domain.comment.CommentResponseDto;
import com.backend.hanghaew7t4clone.domain.comment.CommentService;
import com.backend.hanghaew7t4clone.domain.likes.LikeCountSort;
import com.backend.hanghaew7t4clone.domain.member.Member;
import com.backend.hanghaew7t4clone.global.shared.Check;
import com.backend.hanghaew7t4clone.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CardService {

   private final CardRepository cardRepository;
   private final Check check;
   private final CommentService commentService;


   @Transactional
   public ResponseEntity<?> createCard(CardRequestDto requestDto, HttpServletRequest request) {

      Member member = check.validateMember(request);
      check.accessTokenCheck(request, member);

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
      Card card = check.isPresentCard(id);
      List<CommentResponseDto> commentResponseDtoList = commentService.getCommentResponseDtoList(card);
      return new ResponseEntity<>(Message.success(
              CardResponseDto.builder()
                      .id(card.getId())
                      .nickname(card.getMember().getNickname())
                      .imgUrlList(card.getImgUrlList())
                      .likeCount(card.getLikeCount())
                      .content(card.getContent())
                      .commentCount(card.getCommentCount())
                      .place(card.getPlace())
                      .commentResponseDto(commentResponseDtoList)
                      .createdAt(card.getCreatedAt())
                      .build()
      ), HttpStatus.OK);
   }


   @Transactional(readOnly = true)
   public ResponseEntity<?> getAllCard() {
      List<Card> cards = cardRepository.findAllByOrderByCreatedAtDesc();
      List<CardResponseDto> responseDtoList = new ArrayList<>();
      for (Card card : cards) {
         List<CommentResponseDto> commentList = new ArrayList<>();
         List<Comment> comments = card.getCommentListDto();
         comments.sort(new LikeCountSort());
         int cnt=0;
         for (Comment comment : comments) {
            commentList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .profilePhoto(comment.getMember().getProfilePhoto())
                            .nickname(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .likeCount(comment.getLikeCount())
                            .build());
            cnt++;
            if (cnt>=2) break;
         }
         responseDtoList.add(
                 CardResponseDto.builder()
                         .id(card.getId())
                         .nickname(card.getNickname())
                         .imgUrlList(card.getImgUrlList())
                         .likeCount(card.getLikeCount())
                         .content(card.getContent())
                         .commentCount(card.getCommentCount())
                         .place(card.getPlace())
                         .profilePhoto(card.getMember().getProfilePhoto())
                         .createdAt(card.getCreatedAt())
                         .modifiedAt(card.getModifiedAt())
                         .commentResponseDto(commentList)
                         .build());
      }
      return new ResponseEntity<>(Message.success(responseDtoList), HttpStatus.OK);
   }

   @Transactional
   public ResponseEntity<?> updateCard(Long id, CardRequestDto requestDto, HttpServletRequest request) {
      Member member = check.validateMember(request);
      Card card = check.isPresentCard(id);
      check.accessTokenCheck(request, member);
      check.cardCheck(card);
      check.cardAuthorCheck(member,card);
      card.update(requestDto);
      return new ResponseEntity<>(Message.success(card), HttpStatus.OK);
   }

   @Transactional
   public ResponseEntity<?> deleteCard(Long id, HttpServletRequest request) {

      Member member = check.validateMember(request);
      Card card = check.isPresentCard(id);
      check.accessTokenCheck(request, member);
      check.cardCheck(card);
      check.cardAuthorCheck(member, card);
      cardRepository.delete(card);
      return new ResponseEntity<>(Message.success("delete success"), HttpStatus.OK);
   }

}
