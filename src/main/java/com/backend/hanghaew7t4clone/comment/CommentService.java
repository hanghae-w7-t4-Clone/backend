package com.backend.hanghaew7t4clone.comment;

import com.backend.hanghaew7t4clone.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                            .profilePhoto(comment.getMember().g)
                    .build());
        }

    }
}
