package com.backend.hanghaew7t4clone.comment;

import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    @Transactional
    public ResponseDto<?> getComment(Long CardId) {
        Post post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return ResponseDto.fail("NOT_FOUND", "해당 게시글이 존재하지 않습니다.")
        }
        List<Comment> commentList = commentRepository.findAllByPost(post.get());
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            commentResponseDtoList.add(CommentResponseDto.builder()
                    .id(comment.getId())
                    .profilePhoto(comment.getMember().getProfilephoro())
                    .nickname(comment.getMember().getNickname())
                    .content(comment.getContent())
                    .build());
        }
        return ResponseDto.success(commentResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> createComment(CommentRequestDto requestDto, Long cardId, HttpServletRequest request) {
        if(null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }

        Card card = cardService.isPresentCard(cardId);
        if (null == card) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글입니다.");
        }

        Comment comment = Comment.builder()
                .member(m)
                .build();



    }
}
