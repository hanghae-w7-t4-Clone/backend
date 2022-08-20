package com.backend.hanghaew7t4clone.recomment;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardRepository;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommentService {

    private final TokenProvider tokenProvider;

    private final ReCommentRepository reCommentRepository;

    private final CardRepository cardRepository;

    private final CommentRepository commentRepository;

    // 카드 아이디가 맞는지 검증하고 코멘트 아디기가 맞는지 검증하고 모든 리코멘트의 리스트를 가져와서 보여준다.
    public ResponseDto<?> getAllReComment(Long cardId, Long commentId) {
        Optional<Card> card = cardRepository.findById(cardId);
        Optional<Comment> comment = commentRepository.findById(commentId);
        // 검증
        List<ReComment> reCommentList = reCommentRepository.findAllById(commentId);
        List<ReCommentResponseDto> reCommentResponseDtoList = new ArrayList<>();
        for (ReComment reComment : reCommentList) {
            reCommentResponseDtoList.add(ReCommentResponseDto.builder()
                    .id(reComment.getId())
                    .profilePhoto(reComment.getMember().getProfilePhoto())
                    .nickname(reComment.getMember().getNickname())
                    .content(reComment.getContent())
                    .build());
        }
        return ResponseDto.success(reCommentResponseDtoList);
    }

    public ResponseDto<?> createReComment(ReCommentRequestDto reCommentRequestDto, Long cardId, Long commentId, HttpServletRequest request) {
        Optional<Card> card = cardRepository.findById(cardId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글입니다.");
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        // 검증
        ReComment reComment = ReComment.builder()
                .member(member)
                .comment(comment)
                .content(reCommentRequestDto.getContent())
                .build();
        reCommentRepository.save(reComment);
        return ResponseDto.success("대댓글 작성에 성공하셨습니다.");
    }

    public ResponseDto<?> deleteReComment(Long cardId, Long commentId, Long reCommentId, HttpServletRequest request) {
        Optional<Card> card = cardRepository.findById(cardId);
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment == null) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글입니다.");
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        ReComment reComment = isPresentReComment(reCommentId);
        if (reComment.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 수정할 수 있습니다.");
        }
        if (null == reComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }
        //검증
        reCommentRepository.delete(reComment);
        return ResponseDto.success("대댓글 삭제에 성고하셨습니다.");
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
