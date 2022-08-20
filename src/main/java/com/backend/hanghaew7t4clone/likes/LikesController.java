package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/auth/likes/cards/{id}")
    public ResponseDto<?> CardLikes(@PathVariable Long id, HttpServletRequest request) {
        return likesService.pushCardLikes(id, request);
    }

    @PostMapping("/auth/likes/cards/comments/{id}")
    public ResponseDto<?> CommentLikes(@PathVariable Long id, HttpServletRequest request) {
        return likesService.pushCommentLikes(id, request);
    }

    @PostMapping("auth/re-comments/{id}/Likes")
    public ResponseDto<?> pushReCommentLikes(@PathVariable Long id, HttpServletRequest request) {
        return LikesService.pushReCommentLikes(id, request);
    }

}


