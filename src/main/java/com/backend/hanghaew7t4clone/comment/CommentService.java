package com.backend.hanghaew7t4clone.comment;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.exception.CustomException;
import com.backend.hanghaew7t4clone.exception.ErrorCode;
import com.backend.hanghaew7t4clone.shared.Check;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final Check check;

    @Transactional
    public ResponseEntity<?> getAllComment(Long cardId) {
        Card card = check.isPresentCard(cardId);
        check.cardCheck(card);
        List<Comment> commentsListDto = card.getCommentListDto();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentsListDto) {
            commentResponseDtoList.add(comment.getAllCommentDto());
        }
        return new ResponseEntity<>(Message.success(commentResponseDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> createComment(CommentRequestDto commentRequestDto, Long cardId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.memberCheck(member);
        Card card = check.isPresentCard(cardId);
        check.cardCheck(card);
        check.accessTokenCheck(request, member);
        Comment comment = new Comment(commentRequestDto.getContent(), member, card);
        commentRepository.save(comment);
        return new ResponseEntity<>(Message.success("댓글 작성에 성공하셨습니다."), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long cardId, Long commentId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.memberCheck(member);
        Card card = check.isPresentCard(cardId);
        check.cardCheck(card);
        Comment comment = check.isPresentComment(commentId);
        check.accessTokenCheck(request, member);
        check.commentCheck(comment);
        check.commentAuthorCheck(member, comment);
        commentRepository.delete(comment);
        return new ResponseEntity<>(Message.success("댓글 삭제에 성공하셨습니다."), HttpStatus.OK);
    }
}
