package com.backend.hanghaew7t4clone.domain.likes;

import com.backend.hanghaew7t4clone.domain.card.Card;
import com.backend.hanghaew7t4clone.domain.comment.Comment;
import com.backend.hanghaew7t4clone.global.exception.CustomException;
import com.backend.hanghaew7t4clone.global.exception.ErrorCode;
import com.backend.hanghaew7t4clone.domain.member.Member;
import com.backend.hanghaew7t4clone.domain.recomment.ReComment;
import com.backend.hanghaew7t4clone.global.shared.Check;
import com.backend.hanghaew7t4clone.global.shared.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {
private final Check check;
private final LikesRepository likesRepository;

    @Transactional
    public ResponseEntity <?> pushCardLikes(Long cardId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request,member);
        Card card = check.isPresentCard(cardId);
        if(card==null){throw new CustomException(ErrorCode.CARD_NOT_FOUND);}
        Likes likesToCardByMember = likesRepository.findByCardAndMember(card, member).orElse(null);
        LikesResponseDto likesResponseDto = cardLikeStatus(likesToCardByMember, member, card);
        return new ResponseEntity<>(Message.success(likesResponseDto), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity <?> pushCommentLikes (Long id, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request,member);
        Comment comment = check.isPresentComment(id);
        Likes likesToCommentByMember = likesRepository.findByCommentAndMember(comment, member).orElse(null);
        LikesResponseDto likesResponseDto =commentLikeStatus(likesToCommentByMember,member,comment)    ;
        return new ResponseEntity<>(Message.success(likesResponseDto), HttpStatus.OK);
        }

    @Transactional
    public ResponseEntity <?> pushReCommentLikes(Long id, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request,member);
        ReComment reComment = check.isPresentReComment(id);
        Likes likesToCommentByMember = likesRepository.findByReCommentAndMember(reComment, member).orElse(null);
        LikesResponseDto likesResponseDto =reCommentLikeStatus(likesToCommentByMember,member,reComment)    ;
        return new ResponseEntity<>(Message.success(likesResponseDto), HttpStatus.OK);
    }

    public LikesResponseDto cardLikeStatus(Likes likesByUser, Member member, Card card) {
        if (likesByUser == null) {
            Likes likes = new Likes(member, card);
            likesRepository.save(likes);
            List<Likes> likeList = likesRepository.findAllByCard(card);
            card.updateLikes(likeList.size());
            return new LikesResponseDto(card.getId(), true, "좋아요를 했습니다.");
        } else {
            likesRepository.delete(likesByUser);
            card.discountLikes(likesByUser);
            List<Likes> likeList = likesRepository.findAllByCard(card);
            card.updateLikes(likeList.size());
            return new LikesResponseDto(card.getId(), false, "좋아요를 취소했습니다.");
        }
    }
    public LikesResponseDto commentLikeStatus(Likes likesByUser, Member member, Comment comment) {
        if (likesByUser == null) {
            Likes likes = new Likes(member, comment);
            likesRepository.save(likes);
            List<Likes> likeList = likesRepository.findAllByComment(comment);
            comment.updateLikes(likeList.size());
            return new LikesResponseDto(comment.getId(), true, "좋아요를 했습니다.");
        } else {
            likesRepository.delete(likesByUser);
            comment.discountLikes(likesByUser);
            List<Likes> likeList = likesRepository.findAllByComment(comment);
            comment.updateLikes(likeList.size());
            return new LikesResponseDto(comment.getId(), false, "좋아요를 취소했습니다.");
        }
    }

    public LikesResponseDto reCommentLikeStatus(Likes likesByUser, Member member, ReComment reComment) {
        if (likesByUser == null) {
            Likes likes = new Likes(member, reComment);
            likesRepository.save(likes);
            List<Likes> likeList = likesRepository.findAllByReComment(reComment);
            reComment.updateLikes(likeList.size());
            return new LikesResponseDto(reComment.getId(), true, "좋아요를 했습니다.");
        } else {
            likesRepository.delete(likesByUser);
            reComment.discountLikes(likesByUser);
            List<Likes> likeList = likesRepository.findAllByReComment(reComment);
            reComment.updateLikes(likeList.size());
            return new LikesResponseDto(reComment.getId(), false, "좋아요를 취소했습니다.");
        }
    }



}
