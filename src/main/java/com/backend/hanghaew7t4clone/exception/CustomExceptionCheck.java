package com.backend.hanghaew7t4clone.exception;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.recomment.ReComment;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

@Component
public class CustomExceptionCheck {

    public void cardCheck(Member member, Card card) {
        if (null == card) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        if (!card.getMember().equals(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public void commentCheck(Member member, Card card, Comment comment) {
        if (null == card) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        if (!card.getMember().equals(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (!comment.getMember().equals(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public void reCommentCheck(Member member, Card card, Comment comment, ReComment reComment) {
        if (null == card) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        if (!card.getMember().equals(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (!comment.getMember().equals(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (!reComment.getMember().equals(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public void tokenCheck(HttpServletRequest request, Member member) {
        if (null == request.getHeader("Authorization")) {
            throw new CustomException(ErrorCode.TOKEN_IS_EXPIRED);
        }
        if (null == member) {
            throw new CustomException(ErrorCode.AUTHOR_NOT_FOUND);
        }
    }
}











