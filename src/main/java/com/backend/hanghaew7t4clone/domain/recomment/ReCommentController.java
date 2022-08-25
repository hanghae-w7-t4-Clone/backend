package com.backend.hanghaew7t4clone.domain.recomment;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ReCommentController {

    private final ReCommentService recommentService;

    @GetMapping("/auth/cards/{cardId}/comments/{commentId}/re-comments")
    public ResponseEntity<?> getAllReComment(@PathVariable Long commentId) {
        return recommentService.getAllReComment(commentId);
    }

    @PostMapping("/auth/cards/{cardId}/comments/{commentId}/re-comments")
    public ResponseEntity<?> createReComment(@RequestBody ReCommentRequestDto reCommentRequestDto, @PathVariable Long cardId, @PathVariable Long commentId, HttpServletRequest request) {
        return recommentService.createReComment(reCommentRequestDto, cardId, commentId, request);
    }

    @DeleteMapping("/auth/cards/{cardId}/comments/{commentId}/re-comments/{reCommentId}")
    public ResponseEntity<?> deleteReComment(@PathVariable Long cardId, @PathVariable Long commentId, @PathVariable Long reCommentId, HttpServletRequest request) {
        return recommentService.deleteReComment(cardId, commentId, reCommentId, request);
    }
}
