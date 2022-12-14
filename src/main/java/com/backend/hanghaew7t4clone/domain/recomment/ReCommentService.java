package com.backend.hanghaew7t4clone.domain.recomment;

import com.backend.hanghaew7t4clone.domain.card.Card;
import com.backend.hanghaew7t4clone.domain.comment.Comment;
import com.backend.hanghaew7t4clone.domain.comment.CommentRepository;
import com.backend.hanghaew7t4clone.domain.member.Member;
import com.backend.hanghaew7t4clone.global.shared.Check;
import com.backend.hanghaew7t4clone.global.shared.Message;
import com.backend.hanghaew7t4clone.domain.recomment.ReCommentResponseDto;
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
public class ReCommentService {

    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;
    private final Check check;

    public ResponseEntity<?> getAllReComment(Long commentId) {
        Comment comment = check.isPresentComment(commentId);
        Set<ReComment> reCommentListDto = comment.getReCommentList();
        List<ReCommentResponseDto> reCommentResponseDtoList = new ArrayList<>();
        for (ReComment reComment : reCommentListDto) {
            reCommentResponseDtoList.add(reComment.getAllReCommentDto());
        }
        return new ResponseEntity<>(Message.success(reCommentResponseDtoList), HttpStatus.OK);
    }

    public ResponseEntity<?> createReComment(ReCommentRequestDto reCommentRequestDto, Long cardId, Long commentId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.memberCheck(member);
        Card card = check.isPresentCard(cardId);
        check.cardCheck(card);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        check.accessTokenCheck(request, member);
        check.commentCheck(comment);
        ReComment reComment = new ReComment(reCommentRequestDto.getContent(), member, comment);
        reCommentRepository.save(reComment);
        return new ResponseEntity<>(Message.success("????????? ????????? ?????????????????????."), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteReComment(Long cardId, Long commentId, Long reCommentId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.memberCheck(member);
        Card card = check.isPresentCard(cardId);
        check.cardCheck(card);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        check.commentCheck(comment);
        ReComment reComment = check.isPresentReComment(reCommentId);
        check.accessTokenCheck(request, member);
        check.reCommentAuthorCheck(member,reComment);
        reCommentRepository.delete(reComment);
        return new ResponseEntity<>(Message.success("????????? ????????? ?????????????????????."), HttpStatus.OK);
    }
}
