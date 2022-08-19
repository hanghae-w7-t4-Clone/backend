package com.backend.hanghaew7t4clone.comment;

import com.backend.hanghaew7t4clone.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/cards/{cardId}/comments")
    public ResponseDto<?> getComment(@PathVariable Long cardId) {
        return commentService.getComment(cardId);
    }

    @PostMapping("/auth/cards/{cardId}/comments")
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto, @PathVariable cardId, ) {
        return commentService.createComment(requestDto, cardId);
    }

    @DeleteMapping("/auth/cards/{cardId}/comments")
}
