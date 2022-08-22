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
        Card card = check.isPresentCard(cardId);
        if(card==null) throw new CustomException(ErrorCode.CARD_NOT_FOUND);
        check.tokenCheck(request, member);
        Comment comment = new Comment(commentRequestDto.getContent(), member, card);
        commentRepository.save(comment);
        return new ResponseEntity<>(Message.success("댓글 작성에 성공하셨습니다."), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteComment(Long cardId, Long commentId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Card card = check.isPresentCard(cardId);
        Comment comment = check.isPresentComment(commentId);
        check.tokenCheck(request, member);
        check.commentCheck(member, card, comment);
        commentRepository.delete(comment);
        return new ResponseEntity<>(Message.success("댓글 삭제에 성공하셨습니다."), HttpStatus.OK);
    }
}
