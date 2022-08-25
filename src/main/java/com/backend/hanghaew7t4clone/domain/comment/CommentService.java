package com.backend.hanghaew7t4clone.domain.comment;

import com.backend.hanghaew7t4clone.domain.card.Card;
import com.backend.hanghaew7t4clone.domain.member.Member;
import com.backend.hanghaew7t4clone.global.shared.Check;
import com.backend.hanghaew7t4clone.global.shared.Message;
import com.backend.hanghaew7t4clone.web.comment.CommentRequestDto;
import com.backend.hanghaew7t4clone.web.comment.CommentResponseDto;
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
        List<CommentResponseDto> commentResponseDtoList = getCommentResponseDtoList(card);
        return new ResponseEntity<>(Message.success(commentResponseDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> createComment(CommentRequestDto commentRequestDto, Long cardId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.memberCheck(member);
        Card card = check.isPresentCard(cardId);
        check.cardCheck(card);
        check.commentNullCheck(commentRequestDto);
        check.accessTokenCheck(request, member);
        Comment comment = new Comment(commentRequestDto.getContent(), member, card);
        commentRepository.save(comment);
        int commentCount = commentRepository.findAllByCardOrderByCreatedAtDesc(card).size();
        card.updateComment(commentCount);
        return new ResponseEntity<>(Message.success(CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getMember().getNickname())
                .profilePhoto(comment.getMember().getProfilePhoto())
                .build()), HttpStatus.OK);
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
        return new ResponseEntity<>(Message.success(commentId), HttpStatus.OK);

    }

    public List<CommentResponseDto> getCommentResponseDtoList(Card card) {
        check.cardCheck(card);
        List<Comment> commentsListDto = card.getCommentListDto();
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentsListDto) {
            commentResponseDtoList.add(comment.getAllCommentDto());
        }
        return commentResponseDtoList;
    }
}
