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

    public void memberCheck(Member member){
        if(member==null) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }
    public void cardCheck(Card card) {
        if (null == card) throw new CustomException(ErrorCode.CARD_NOT_FOUND);
    }
    public void cardAuthorCheck(Member member, Card card){
        if (!card.getMember().equals(member)) throw new CustomException(ErrorCode.NOT_AUTHOR);
    }
    public void commentCheck(Comment comment) {
        if (null == comment) throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
    }
    public void commentAuthorCheck(Member member, Comment comment){
        if (!comment.getMember().equals(member))  throw new CustomException(ErrorCode.NOT_AUTHOR);
    }

    public void reCommentAuthorCheck(Member member,ReComment reComment) {
        if (!reComment.getMember().equals(member)) throw new CustomException(ErrorCode.NOT_AUTHOR);
    }

    public void accessTokenCheck(HttpServletRequest request, Member member) {
        if (null == request.getHeader("Authorization")) throw new CustomException(ErrorCode.TOKEN_IS_EXPIRED);
        if (null == member) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
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











