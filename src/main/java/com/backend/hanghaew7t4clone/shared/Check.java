package com.backend.hanghaew7t4clone.shared;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardRepository;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.exception.CustomException;
import com.backend.hanghaew7t4clone.exception.ErrorCode;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.recomment.ReComment;
import com.backend.hanghaew7t4clone.recomment.ReCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Check {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;
    private  final TokenProvider tokenProvider;

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
        if (null == comment) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
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

    @Transactional(readOnly = true)
    public Card isPresentCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return optionalCard.orElse(null);
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    @Transactional(readOnly = true)
    public ReComment isPresentReComment(Long id) {
        Optional<ReComment> optionalSubComment = reCommentRepository.findById(id);
        return optionalSubComment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}











