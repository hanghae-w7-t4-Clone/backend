package com.backend.hanghaew7t4clone.member;


import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.exception.CustomException;
import com.backend.hanghaew7t4clone.exception.ErrorCode;
import com.backend.hanghaew7t4clone.jwt.RefreshToken;
import com.backend.hanghaew7t4clone.jwt.RefreshTokenRepository;
import com.backend.hanghaew7t4clone.jwt.TokenDto;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${ACCESS_TOKEN_EXPIRE_TIME}")
    TemporalAmount accessTokenExpireTime;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseDto<?> creatMember(MemberRequestDto requestDto) {
        if (null != isPresentMember(requestDto.getNickname())) {
            return ResponseDto.fail("DUPLICATED_NICKNAME", "중복된 닉네임 입니다.");
        }
        Member member;
        if (requestDto.getLoginId().matches("\\d{10,11}")) {
            member = Member.builder()
                    .nickname(requestDto.getNickname())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .name(requestDto.getName())
                    .phoneNum(requestDto.getLoginId())
                    .build();
            memberRepository.save(member);
            return ResponseDto.success("CreatMember Success");
        } else if (requestDto.getLoginId().matches("[a-zA-Z\\d]{3,15}@[a-zA-Z\\d]{3,15}[.][a-zA-Z]{2,5}")) {
            member = Member.builder()
                    .nickname(requestDto.getNickname())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .email(requestDto.getLoginId())
                    .name(requestDto.getName())
                    .build();
            memberRepository.save(member);
            return ResponseDto.success("CreatMember Success");
        }
        return ResponseDto.fail("BAD_REQUEST","로그인 아이디가 올바르지 않습니다");
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = defineId(requestDto.getLoginId());
        if (member == null) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");}

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("INVALID_MEMBER", "사용자를 찾을 수 없습니다.");
        }
        String nickname =member.getNickname();

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(nickname);

    }


    @Transactional(readOnly = true)
    public Member defineId(String loginId){
        if (loginId.matches("\\d{10,11}")) {
            return memberRepository.findByPhoneNum(loginId).orElse(null);
        } else if (loginId.matches("[a-zA-Z\\d]{3,15}@[a-zA-Z\\d]{3,15}[.][a-zA-Z]{2,5}")) {
            return memberRepository.findByEmail(loginId).orElse(null);
        } else {
            return memberRepository.findByNickname(loginId).orElse(null);
        }
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


    public ResponseDto<?> refreshToken(String nickname,HttpServletRequest request, HttpServletResponse response) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("REFRESH_TOKEN_NOT_FOUND",
                    "로그인 시간이 만료되었습니다.");
        }
        Member requestingMember = memberRepository.findByNickname(nickname).orElse(null);
        if (requestingMember == null) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인 정보가 맞지 않습니다.");
        }
        RefreshToken refreshTokenConfirm = refreshTokenRepository.findByMember(requestingMember).orElse(null);
        if(refreshTokenConfirm==null){
            return ResponseDto.fail("REFRESH_TOKEN_NOT_FOUND", "로그인 정보가 맞지 않습니다.");
        }
        LocalDateTime currentDateTime =LocalDateTime.now();
        if(!refreshTokenConfirm.getCreatedAt().plus(accessTokenExpireTime).equals(currentDateTime)){
            return ResponseDto.fail("REFRESH_TOKEN_NOT_FOUND", "로그인 정보가 맞지 않습니다.");
        }
        if (Objects.equals(refreshTokenConfirm.getValue(), request.getHeader("Refresh-Token"))) {
                TokenDto tokenDto = tokenProvider.generateTokenDto(requestingMember);
                accessTokenToHeaders(tokenDto, response);
                return ResponseDto.success("ACCESS_TOKEN_REISSUE");
            } else {
            tokenProvider.deleteRefreshToken(memberRepository.findByNickname(nickname).orElseThrow(
                    () -> new CustomException(ErrorCode.INVALID_TOKEN))
             );
                return ResponseDto.fail("REFRESH_TOKEN_NOT_FOUND", "로그인 정보가 맞지 않습니다.");
            }
        }

    public void accessTokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }
}
