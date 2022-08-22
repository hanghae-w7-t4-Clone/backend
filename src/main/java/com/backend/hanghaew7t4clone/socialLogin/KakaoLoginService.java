//package com.backend.hanghaew7t4clone.socialLogin;
//
//
//import com.backend.hanghaew7t4clone.jwt.TokenDto;
//import com.backend.hanghaew7t4clone.jwt.TokenProvider;
//import com.backend.hanghaew7t4clone.jwt.UserDetailsImpl;
//import com.backend.hanghaew7t4clone.member.Member;
//import com.backend.hanghaew7t4clone.member.MemberRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//
//import java.util.UUID;
//import java.util.function.Consumer;
//
//@Service
//@RequiredArgsConstructor
//public class KakaoLoginService {
//
//    private final KakaoConfigUtils configUtils;
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final TokenProvider tokenProvider;
//
//
//    public ResponseEntity login(String jwtToken) {
//        // HTTP 통신을 위해 RestTemplate 활용
//        try {
//            // 2. "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
//            String nickname = getKakaoMemberInfo(jwtToken);
//            // 3. "카카오 사용자 정보"로 필요시 회원가입
//            Member kakaoMember = registerKakaoMemberIfNeeded(nickname);
//            // 4. 강제 로그인 처리
//            return forceLogin(kakaoMember);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.badRequest().body(null);
//    }
//
//    private String getKakaoMemberInfo(String accessToken) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoMemberInfoRequest = new HttpEntity<>(headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//            "https://kapi.kakao.com/v2/user/me",
//            HttpMethod.POST,
//            kakaoMemberInfoRequest,
//            String.class
//        );
//
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        String nickname = jsonNode.get("properties")
//            .get("nickname").asText();
//        return nickname;
//    }
//
//    private Member registerKakaoMemberIfNeeded(String email) {
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        String kakao = "kakao";
//        Member kakaoMember = MemberRepository.findByEmailAndType(email, kakao)
//            .orElse(null);
//        if (kakaoMember == null) {
//            // 회원가입
//
//            // password: random UUID
//            String password = UUID.randomUUID().toString();
//            String encodedPassword = passwordEncoder.encode(password);
//            String type = "kakao";
//
//            String nickname = "";
//            kakaoMember = Member.builder()
//                    .name()
//                    .email()
//                    .nickname()
//                    .phoneNum()
//                    .password()
//                    .build(email, encodedPassword, nickname, type);
//            memberRepository.save(kakaoMember);
//        }
//
//        return kakaoMember;
//    }
//
//    private ResponseEntity forceLogin(Member kakaoMember) {
//        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoMember);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Token 생성
//        TokenDto tokenDto = tokenProvider.generateTokenDto(kakaoMember);
//
//        memberRepository.save(kakaoMember);
//
//
//        return ResponseEntity.ok()
//                .headers((Consumer<HttpHeaders>) tokenDto)
//            .body(new SocialLoginResponseDto(kakaoMember));
//
//    }
//}
