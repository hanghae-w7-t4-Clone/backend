package com.backend.hanghaew7t4clone.web.member;


import com.backend.hanghaew7t4clone.domain.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberRequestDto requestDto){
        return memberService.creatMember(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        return memberService.login(requestDto, response);
    }

    @PostMapping("/nick-check")
    public ResponseEntity<?> nickCheck(@RequestBody String loginId){
        return memberService.nickCheck(loginId);
    }

    @PostMapping("/login-id-check")
    public ResponseEntity<?> loginIdCheck(@RequestBody String loginId){
        return memberService.loginIdCheck(loginId);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokenCheck( HttpServletRequest request, HttpServletResponse response){
        return memberService.refreshToken(request, response);
    }
}
