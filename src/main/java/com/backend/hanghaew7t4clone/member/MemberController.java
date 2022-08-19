package com.backend.hanghaew7t4clone.member;

import com.backend.hanghaew7t4clone.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/users/signup")
    public ResponseDto<?> signup(@RequestBody MemberRequestDto requestDto){
        return memberService.creatMember(requestDto);
    }

    @PostMapping("/users/login")
    public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        return memberService.login(requestDto, response);
    }

    @PostMapping("/users/nick-check")
    public ResponseDto<?> nickCheck(@RequestBody String loginId){
        return memberService.nickCheck(loginId);
    }
}
