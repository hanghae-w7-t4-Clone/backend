package com.backend.hanghaew7t4clone.recomment;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardService;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.exception.CustomExceptionCheck;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecommentService {

    private final TokenProvider tokenProvider;
    private final ReCommentRepository reCommentRepository;
    private final CardService cardService;
    private final CommentRepository commentRepository;
    private final CustomExceptionCheck customExceptionCheck;

    public ResponseDto<?> getAllReComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        Set<ReComment> reCommentListDto = comment.get().getReCommentList();
        List<ReCommentResponseDto> reCommentResponseDtoList = new ArrayList<>();
        for (ReComment reComment : reCommentListDto) {
            reCommentResponseDtoList.add(reComment.getAllReCommentDto());
        }
        return ResponseDto.success(reCommentResponseDtoList);
    }

    public ResponseDto<?> createReComment(ReCommentRequestDto reCommentRequestDto, Long cardId, Long commentId, HttpServletRequest request) {
        Member member = validateMember(request);
        Card card = cardService.isPresentCard(cardId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        customExceptionCheck.tokenCheck(request, member);
        customExceptionCheck.commentCheck(member, card, comment);
        ReComment reComment = new ReComment(reCommentRequestDto.getContent(), member, comment);
        reCommentRepository.save(reComment);
        return ResponseDto.success("대댓글 작성에 성공하셨습니다.");
    }

    public ResponseDto<?> deleteReComment(Long cardId, Long commentId, Long reCommentId, HttpServletRequest request) {
        Member member = validateMember(request);
        Card card = cardService.isPresentCard(cardId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        ReComment reComment = isPresentReComment(reCommentId);
        customExceptionCheck.tokenCheck(request, member);
        customExceptionCheck.reCommentCheck(member, card, comment, reComment);
        reCommentRepository.delete(reComment);
        return ResponseDto.success("대댓글 삭제에 성공하셨습니다.");
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
