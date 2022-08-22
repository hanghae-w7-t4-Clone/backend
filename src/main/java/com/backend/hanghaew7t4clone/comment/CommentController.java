package com.backend.hanghaew7t4clone.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/cards/{cardId}/comments")
    public ResponseEntity<?> getAllComment(@PathVariable Long cardId) {
        return commentService.getAllComment(cardId);
    }

    @PostMapping("/auth/cards/{cardId}/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long cardId, HttpServletRequest request) {
        return commentService.createComment(requestDto, cardId, request);
    }

    @DeleteMapping("/auth/cards/{cardId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long cardId, @PathVariable Long commentId, HttpServletRequest request) {
        return commentService.deleteComment(cardId, commentId, request);
    }
}