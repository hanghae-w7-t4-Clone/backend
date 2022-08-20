package com.backend.hanghaew7t4clone.comment;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardRepository;
import com.backend.hanghaew7t4clone.card.CardService;
import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.member.jwt.TokenProvider;
import com.backend.hanghaew7t4clone.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CardRepository cardRepository;

    private final CardService cardService;

    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> getAllComment(Long cardId) {
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "해당 게시글이 존재하지 않습니다.");
        }
        List<Comment> commentList = commentRepository.findAllByCard(card.get());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(CommentResponseDto.builder()
                    .id(comment.getId())
                    .profilePhoto(comment.getMember().getProfilePhoto())
                    .nickname(comment.getMember().getNickname())
                    .content(comment.getContent())
                    .build());
        }
        return ResponseDto.success(commentResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto commentRequestDto, Long cardId, HttpServletRequest request) {
        if(null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Card card = cardService.isPresentCard(cardId);
        if (null == card) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글입니다.");
        }

        Comment comment = Comment.builder()
                .member(member)
                .card(card)
                .content(commentRequestDto.getContent())
                .build();
        commentRepository.save(comment);
        return ResponseDto.success(commentRequestDto);
    }

    @Transactional
    public ResponseDto<?> deleteComment(Long cardId, Long commentId, HttpServletRequest request) {
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Card card = cardService.isPresentCard(cardId);
        if (null == card) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글입니다.");
        }

        Comment comment = isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        if (comment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
        commentRepository.delete(comment);
        return ResponseDto.success("삭제 완료");
    }

    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
