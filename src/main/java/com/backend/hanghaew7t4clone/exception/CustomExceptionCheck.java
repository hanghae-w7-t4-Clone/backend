package com.backend.hanghaew7t4clone.exception;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardRepository;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.recomment.ReComment;
import com.backend.hanghaew7t4clone.recomment.ReCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class CustomExceptionCheck {
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final ReCommentRepository reCommentRepository;
    private  final TokenProvider tokenProvider;

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

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            return optionalComment.orElseThrow();
        } else {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);}
    }

    @Transactional(readOnly = true)
    public ReComment isPresentReComment(Long id) {
        Optional<ReComment> optionalReComment = reCommentRepository.findById(id);
        if (optionalReComment.isPresent()) {
            return optionalReComment.orElseThrow();
        } else {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);}

    }

    @Transactional(readOnly = true)
    public Card isPresentCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            return optionalCard.orElseThrow();
        } else {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);}
    }
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}











