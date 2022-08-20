package com.backend.hanghaew7t4clone.exception;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.recomment.ReComment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class CustomExceptionCheck {

    public void cardCheck(Member member, @Nullable Card card, @Nullable Comment comment, @Nullable ReComment reComment) {
        if (null == card) {
            throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        }
        if (!card.getMember().equals(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (!Objects.requireNonNull(comment).getMember().equals(member)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        if (!Objects.requireNonNull(reComment).getMember().equals(member)) {
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











