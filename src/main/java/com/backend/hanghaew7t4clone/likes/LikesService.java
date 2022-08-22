package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.shared.Check;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.recomment.ReComment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

private final LikesRepository likesRepository;

private final Check check;

    @Transactional
    public ResponseDto<?> pushCardLikes(Long cardId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.tokenCheck(request,member);
        Card card = check.isPresentCard(cardId);
        Likes likesToCardByMember = likesRepository.findByCardAndMember(card, member).orElse(null);
        LikesResponseDto likesResponseDto = likeStatus(likesToCardByMember, member, card,null,null);
        return ResponseDto.success(likesResponseDto);

    }

    @Transactional
    public ResponseDto<?>pushCommentLikes (Long id, HttpServletRequest request) {

        Member member = check.validateMember(request);
        check.tokenCheck(request,member);

        Comment comment = check.isPresentComment(id);

        Likes likesToCommentByMember = likesRepository.findByCommentAndMember(comment, member).orElse(null);
        LikesResponseDto likesResponseDto =likeStatus(likesToCommentByMember,member,null,comment,null)    ;
        return ResponseDto.success(likesResponseDto);
        }

    @Transactional
    public ResponseDto<?> pushReCommentLikes(Long id, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.tokenCheck(request,member);
        ReComment reComment = check.isPresentReComment(id);
        Likes likesToCommentByMember = likesRepository.findByReCommentAndMember(reComment, member).orElse(null);
        LikesResponseDto likesResponseDto =likeStatus(likesToCommentByMember,member,null,null,reComment)    ;
        return ResponseDto.success(likesResponseDto);
    }

    public LikesResponseDto likeStatus(Likes likesByUser, Member member, @Nullable Card card, @Nullable Comment comment, @Nullable ReComment reComment) {
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
            if(reComment!=null){
                Likes likes = new Likes(member, reComment);
                likesRepository.save(likes);
                return new LikesResponseDto(reComment.getId(), true, "좋아요를 했습니다.");
            }
        }else{
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
            if(reComment!=null){
                likesRepository.delete(likesByUser);
                reComment.discountLikes(likesByUser);
                return new LikesResponseDto(reComment.getId(), false, "좋아요를 취소했습니다.");
            }
        }
        return null;
    }



}
