package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardRepository;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import com.backend.hanghaew7t4clone.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

private final LikesRepository likesRepository;
private final TokenProvider tokenProvider;
private final CardRepository cardRepository;
private final CommentRepository commentRepository;


    @Transactional
    public ResponseDto<?> pushCardLikes(Long cardId, HttpServletRequest request) {
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Card card = isPresentCard(cardId);
        if (card == null) {
            return ResponseDto.fail("CARD_NOT_FOUND", "게시글이 존재하지 않습니다.");
        }
        Likes likesToCardByMember = likesRepository.findByCardAndMember(card, member).orElse(null);
        LikesResponseDto likesResponseDto = likeStatus(likesToCardByMember, member, card,null);
        return ResponseDto.success(likesResponseDto);

    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Card isPresentCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return optionalCard.orElse(null);
    }

    @Transactional
    public ResponseDto<?>pushCommentLikes (Long id, HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment =isPresentComment(id);
        if (comment==null){ return ResponseDto.fail("COMMENT_NOT_FOUND", "댓글이 존재하지 않습니다.");
        }

        Likes likesToCommentByMember = likesRepository.findByCommentAndMember(comment, member).orElse(null);
        LikesResponseDto likesResponseDto =likeStatus(likesToCommentByMember,member,null,comment)    ;
        return ResponseDto.success(likesResponseDto);
        }

    public LikesResponseDto likeStatus(Likes likesByUser, Member member, @Nullable Card card, @Nullable Comment comment) {
        if (likesByUser == null) {
            if (card != null) {
                Likes likes = new Likes(member, card);
                likesRepository.save(likes);
                return new LikesResponseDto(card.getId(), true, "좋아요를 했습니다.");
            }
            if(comment!=null){
                Likes likes = new Likes(member, comment);
                likesRepository.save(likes);
                return new LikesResponseDto(comment.getId(), true, "좋아요를 했습니다.");
            }
        } else {
            if (card != null)  {
                likesRepository.delete(likesByUser);
                card.discountLikes(likesByUser);
                return new LikesResponseDto(card.getId(), false, "좋아요를 취소했습니다.");
            }
            if(comment!=null){
                likesRepository.delete(likesByUser);
                comment.discountLikes(likesByUser);
                return new LikesResponseDto(comment.getId(), false, "좋아요를 취소했습니다.");
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }


}
