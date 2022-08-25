package com.backend.hanghaew7t4clone.domain.member;


import com.backend.hanghaew7t4clone.global.exception.CustomException;
import com.backend.hanghaew7t4clone.global.exception.ErrorCode;
import com.backend.hanghaew7t4clone.global.jwt.RefreshToken;
import com.backend.hanghaew7t4clone.global.jwt.RefreshTokenRepository;
import com.backend.hanghaew7t4clone.global.jwt.TokenDto;
import com.backend.hanghaew7t4clone.global.jwt.TokenProvider;
import com.backend.hanghaew7t4clone.global.shared.Message;
import com.backend.hanghaew7t4clone.web.member.LoginRequestDto;
import com.backend.hanghaew7t4clone.web.member.LoginResponseDto;
import com.backend.hanghaew7t4clone.web.member.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseEntity<?>creatMember(MemberRequestDto requestDto) {
        if (null != isPresentMember(requestDto.getNickname())) {
            throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
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
            return new ResponseEntity<>(Message.success(null),HttpStatus.OK);
        } else if (requestDto.getLoginId().matches("[a-zA-Z\\d]{3,15}@[a-zA-Z\\d]{3,15}[.][a-zA-Z]{2,5}")) {
            member = Member.builder()
                    .nickname(requestDto.getNickname())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .email(requestDto.getLoginId())
                    .name(requestDto.getName())
                    .build();
            memberRepository.save(member);
            return new ResponseEntity<>(Message.success(null),HttpStatus.OK);
        }
        throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = defineId(requestDto.getLoginId());
        if (member == null) {
            throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);}
        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);
        }
        String nickname =member.getNickname();
        String photoUrl = member.getProfilePhoto();
        LoginResponseDto loginResponseDto = new LoginResponseDto(nickname,photoUrl);
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);
        return new ResponseEntity<>(Message.success(loginResponseDto),HttpStatus.OK);
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
    public ResponseEntity<?> nickCheck(String loginId){
        if (null != isPresentMember(loginId)) {
           throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);}
        return new ResponseEntity<>(Message.success(null),HttpStatus.OK);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }


    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
       tokenProvider.validateToken(request.getHeader("Refresh-Token"));
        Member requestingMember = validateMember(request);
       long accessTokenExpire = Long.parseLong(request.getHeader("Access-Token-Expire-Time"));
        long now = (new Date().getTime());
        if (now>accessTokenExpire){
            tokenProvider.deleteRefreshToken(requestingMember);
            throw new CustomException(ErrorCode.INVALID_TOKEN);}

        RefreshToken refreshTokenConfirm = refreshTokenRepository.findByMember(requestingMember).orElse(null);
        if (refreshTokenConfirm == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
        }
        if (Objects.equals(refreshTokenConfirm.getValue(), request.getHeader("Refresh-Token"))) {
            TokenDto tokenDto = tokenProvider.generateAccessTokenDto(requestingMember);
            accessTokenToHeaders(tokenDto, response);
            return new ResponseEntity<>(Message.success("ACCESS_TOKEN_REISSUE"), HttpStatus.OK);
        } else {
            tokenProvider.deleteRefreshToken(requestingMember);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
    public void accessTokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    public ResponseEntity<?> loginIdCheck(String loginId) {
        if (loginId.matches("\\d{10,11}")) {
            if (memberRepository.findByPhoneNum(loginId).isPresent()) {
                throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
            }
            ;
        } else if (loginId.matches("[a-zA-Z\\d]{3,15}@[a-zA-Z\\d]{3,15}[.][a-zA-Z]{2,5}")) {
            if (memberRepository.findByEmail(loginId).isPresent()) {
                throw new CustomException(ErrorCode.DUPLICATED_NICKNAME);
            }
        }
        return new ResponseEntity<>("사용이 가능합니다.", HttpStatus.OK);
    }
}
