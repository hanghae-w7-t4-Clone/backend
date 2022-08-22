package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.comment.CommentResponseDto;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.shared.Check;
import com.backend.hanghaew7t4clone.shared.Message;
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
   private final CommentRepository commentRepository;
   private final Check check;


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
      check.cardCheck(card);
      List<Comment> commentsListDto = card.getCommentListDto();
      List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
      for (Comment comment : commentsListDto) {
         commentResponseDtoList.add(comment.getAllCommentDto());
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
         List<Comment> comments = commentRepository.findTop2ByCardOrderByLikesList(card);
         for (Comment comment : comments) {
            commentList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
//                            .profilePhoto(comment.get)
                            .nickname(card.getNickname())
                            .content(card.getContent())
                            .build());
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
//      //cascade 안먹을때 여기
//      List<Comment> commentList = commentRepository.findAllByCard(card);
//      for (Comment comment : commentList) {
//         commentRepository.delete(comment);
//      }
      cardRepository.delete(card);
      return new ResponseEntity<>(Message.success("delete success"), HttpStatus.OK);
   }

}
