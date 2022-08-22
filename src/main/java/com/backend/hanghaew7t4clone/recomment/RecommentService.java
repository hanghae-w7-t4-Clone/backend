package com.backend.hanghaew7t4clone.recomment;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.exception.CustomExceptionCheck;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecommentService {

    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;
    private final CustomExceptionCheck customExceptionCheck;

    public ResponseEntity<?> getAllReComment(Long commentId) {
        Comment comment = customExceptionCheck.isPresentComment(commentId);
        Set<ReComment> reCommentListDto = comment.getReCommentList();
        List<ReCommentResponseDto> reCommentResponseDtoList = new ArrayList<>();
        for (ReComment reComment : reCommentListDto) {
            reCommentResponseDtoList.add(reComment.getAllReCommentDto());
        }
        return new ResponseEntity<>(Message.success(reCommentResponseDtoList), HttpStatus.OK);
    }

    public ResponseEntity<?> createReComment(ReCommentRequestDto reCommentRequestDto, Long cardId, Long commentId, HttpServletRequest request) {
        Member member = customExceptionCheck.validateMember(request);
        Card card = customExceptionCheck.isPresentCard(cardId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        customExceptionCheck.tokenCheck(request, member);
        customExceptionCheck.commentCheck(member, card, comment);
        ReComment reComment = new ReComment(reCommentRequestDto.getContent(), member, comment);
        reCommentRepository.save(reComment);
        return new ResponseEntity<>(Message.success("대댓글 작성에 성공하셨습니다."), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteReComment(Long cardId, Long commentId, Long reCommentId, HttpServletRequest request) {
        Member member = customExceptionCheck.validateMember(request);
        Card card = customExceptionCheck.isPresentCard(cardId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        ReComment reComment = customExceptionCheck.isPresentReComment(reCommentId);
        customExceptionCheck.tokenCheck(request, member);
        customExceptionCheck.reCommentCheck(member, card, comment, reComment);
        reCommentRepository.delete(reComment);
        return new ResponseEntity<>(Message.success("대댓글 삭제에 성공하셨습니다."), HttpStatus.OK);
    }
}
