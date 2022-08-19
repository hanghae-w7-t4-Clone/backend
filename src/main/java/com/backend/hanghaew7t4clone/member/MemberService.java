package com.backend.hanghaew7t4clone.member;


import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.jwt.TokenDto;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> creatMember(MemberRequestDto requestDto) {
        if (null != isPresentMember(requestDto.getNickname())) {
            return ResponseDto.fail("DUPLICATED_NICKNAME", "중복된 닉네임 입니다.");
        }

        if (requestDto.getEmail()==null){
            Member member = Member.builder()
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .phoneNum(requestDto.getPhoneNum())
                .build();

            memberRepository.save(member);
        }else {
            Member member = Member.builder()
                    .nickname(requestDto.getNickname())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .email(requestDto.getEmail())
                    .name(requestDto.getName())
                    .build();

            memberRepository.save(member);
        }

        return ResponseDto.success("CreatMember Success");
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentMember(requestDto.getLoginId());
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("INVALID_MEMBER", "사용자를 찾을 수 없습니다.");
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success("login success");
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> nickCheck(String loginId){
        if (null != isPresentMember(loginId)) {
            return ResponseDto.fail("DUPLICATED_NICKNAME", "중복된 닉네임 입니다.");
        }
        return ResponseDto.success("NICK_CHECK_SUCCESS");
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }


}
