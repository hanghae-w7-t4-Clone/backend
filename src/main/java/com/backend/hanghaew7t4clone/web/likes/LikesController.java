package com.backend.hanghaew7t4clone.web.likes;


import com.backend.hanghaew7t4clone.domain.likes.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/likes/cards")
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/{id}")
    public ResponseEntity<?> CardLikes(@PathVariable Long id, HttpServletRequest request) {
        return likesService.pushCardLikes(id, request);
    }

    @PostMapping("/comments/{id}")
    public ResponseEntity<?> CommentLikes(@PathVariable Long id, HttpServletRequest request) {
        return likesService.pushCommentLikes(id, request);
    }

    @PostMapping("/comments/re-comments/{id}")
    public ResponseEntity<?> pushReCommentLikes(@PathVariable Long id, HttpServletRequest request) {
        return likesService.pushReCommentLikes(id, request);
    }

}


