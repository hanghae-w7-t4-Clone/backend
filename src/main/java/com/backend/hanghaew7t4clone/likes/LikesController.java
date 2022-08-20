package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/auth/likes/cards/{id}")
    public ResponseDto<?> CardLike(@PathVariable Long id, HttpServletRequest request) {
        return likeService.pushCardLike(id, request);
    }

    @PostMapping("/auth/likes/cards/comments/{id}")
    public ResponseDto<?> CommentLike(@PathVariable Long id, HttpServletRequest request) {
        return likeService.pushCommentLike(id, request);
    }

//    @PostMapping("auth/re-comments/{id}/like")
//    public ResponseDto<?> pushReCommentLike(@PathVariable Long id, HttpServletRequest request) {
//        return likeService.pushReCommentLike(id, request);
//    }

}


