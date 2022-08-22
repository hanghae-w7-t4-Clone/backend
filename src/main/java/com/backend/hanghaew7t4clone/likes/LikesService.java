package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardRepository;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.exception.CustomException;
import com.backend.hanghaew7t4clone.exception.CustomExceptionCheck;
import com.backend.hanghaew7t4clone.exception.ErrorCode;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.recomment.ReComment;
import com.backend.hanghaew7t4clone.recomment.ReCommentRepository;
import com.backend.hanghaew7t4clone.shared.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

private final LikesRepository likesRepository;
private final CardRepository cardRepository;
private final CommentRepository commentRepository;
private final ReCommentRepository reCommentRepository;
private final CustomExceptionCheck customExceptionCheck;

    @Transactional
    public ResponseEntity<?> pushCardLikes(Long cardId, HttpServletRequest request) {
        Member member = customExceptionCheck.validateMember(request);
        customExceptionCheck.tokenCheck(request,member);
        Card card = customExceptionCheck.isPresentCard(cardId);
        if(card==null){throw new CustomException(ErrorCode.CARD_NOT_FOUND);}
        Likes likesToCardByMember = likesRepository.findByCardAndMember(card, member).orElse(null);
        LikesResponseDto likesResponseDto = likeStatus(likesToCardByMember, member, card,null,null);
        return new ResponseEntity<>(Message.success(likesResponseDto), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> pushCommentLikes (Long id, HttpServletRequest request) {

        Member member = customExceptionCheck.validateMember(request);
        customExceptionCheck.tokenCheck(request,member);

        Comment comment =customExceptionCheck.isPresentComment(id);

        Likes likesToCommentByMember = likesRepository.findByCommentAndMember(comment, member).orElse(null);
        LikesResponseDto likesResponseDto =likeStatus(likesToCommentByMember,member,null,comment,null)    ;
        return new ResponseEntity<>(Message.success(likesResponseDto), HttpStatus.OK);
        }

    @Transactional
    public ResponseEntity<?> pushReCommentLikes(Long id, HttpServletRequest request) {
        Member member = customExceptionCheck.validateMember(request);
        customExceptionCheck.tokenCheck(request,member);
        ReComment reComment =customExceptionCheck.isPresentReComment(id);
        Likes likesToCommentByMember = likesRepository.findByReCommentAndMember(reComment, member).orElse(null);
        LikesResponseDto likesResponseDto =likeStatus(likesToCommentByMember,member,null,null,reComment)    ;
        return new ResponseEntity<>(Message.success(likesResponseDto), HttpStatus.OK);
    }

    public LikesResponseDto likeStatus(Likes likesByUser, Member member, @Nullable Card card, @Nullable Comment comment, @Nullable ReComment reComment) {
        if (likesByUser == null) {
            if (card != null) {
                Likes likes = new Likes(member, card);
                likesRepository.save(likes);
                card.updateLikes();
                return new LikesResponseDto(card.getId(), true, "좋아요를 했습니다.");
            }
            if(comment!=null){
                Likes likes = new Likes(member, comment);
                likesRepository.save(likes);
                comment.updateLikes();
                return new LikesResponseDto(comment.getId(), true, "좋아요를 했습니다.");
            }
            if(reComment!=null){
                Likes likes = new Likes(member, reComment);
                likesRepository.save(likes);
                reComment.updateLikes();
                return new LikesResponseDto(reComment.getId(), true, "좋아요를 했습니다.");
            }
        }else{
            if (card != null)  {
                likesRepository.delete(likesByUser);
                card.discountLikes(likesByUser);
                card.updateLikes();
                return new LikesResponseDto(card.getId(), false, "좋아요를 취소했습니다.");
            }
            if(comment!=null){
                likesRepository.delete(likesByUser);
                comment.discountLikes(likesByUser);
                comment.updateLikes();
                return new LikesResponseDto(comment.getId(), false, "좋아요를 취소했습니다.");
            }
            if(reComment!=null){
                likesRepository.delete(likesByUser);
                reComment.discountLikes(likesByUser);
                reComment.updateLikes();
                return new LikesResponseDto(reComment.getId(), false, "좋아요를 취소했습니다.");
            }
        }
        return null;
    }



}
