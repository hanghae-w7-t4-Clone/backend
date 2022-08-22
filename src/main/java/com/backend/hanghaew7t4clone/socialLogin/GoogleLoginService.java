//package com.backend.hanghaew7t4clone.socialLogin;
//
//
//import com.backend.hanghaew7t4clone.jwt.TokenProvider;
//import com.backend.hanghaew7t4clone.jwt.UserDetailsImpl;
//import com.backend.hanghaew7t4clone.member.Member;
//import com.backend.hanghaew7t4clone.member.MemberRepository;
//import com.backend.hanghaew7t4clone.shared.Message;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class GoogleLoginService {
//
//    private final GoogleConfigUtils configUtils;
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    private final TokenProvider tokenProvider;
//
//    // token을 이용하여 사용자 정보 획득
//    public ResponseEntity login(String jwtToken) {
//        // HTTP 통신을 위해 RestTemplate 활용
//        try {
//            String email = getGoogleMemberInfo(jwtToken);
//            Member googleMember = registerGoogleMemberIfNeeded(email);
//            return forceLogin(googleMember);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.badRequest().body(null);
//    }
//
//    private ResponseEntity<?> forceLogin(Member googleMember) {
//        UserDetailsImpl userDetails = new UserDetailsImpl(googleMember);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//
//        // Token 생성//리프레시 토큰도 생성
//        final String token = tokenProvider.generateTokenDto(googleMember);
//
//        memberRepository.save(googleMember);
//
//
//        return new ResponseEntity<>(Message.success(null), HttpStatus.OK)
//    }
//
//
//
//    private Member registerGoogleMemberIfNeeded(String email) {
//        String google = "google";
//        Member googleMember = memberRepository.findByEmailAndType(email, google).orElse(null);
//
//        if (googleMember == null) {
//
//            // password: random UUID
//            String password = UUID.randomUUID().toString();
//            String encodedPassword = passwordEncoder.encode(password);
//            String type = "google";
//
//            String nickname = "";
//            googleMember = new Member(email, encodedPassword, nickname, type);
//            MemberRepository.save(googleMember);
//
//            // 사용자 포인트 부여
//            int mypoint = 0;
//            int postCount = 1;
//            int commentCount = 5;
//            int lottoCount = 1;
//            int recommendCount = 0;
//            Point point = new Point(mypoint, googleMember, postCount, commentCount, lottoCount, recommendCount);
//            pointRepository.save(point);
//        }
//        return googleMember;
//    }
//
//    private String getGoogleMemberInfo(String jwtToken) throws JsonProcessingException {
//        // JWT Token을 전달해 JWT 저장된 사용자 정보 확인
//        String requestUrl = UriComponentsBuilder.fromHttpUrl(configUtils.getGoogleAuthUrl() + "/tokeninfo").
//            queryParam("id_token", jwtToken).toUriString();
//
//        HttpHeaders headers = new HttpHeaders();
//        String body = "";
//        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
//
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//            requestUrl,
//            HttpMethod.GET,
//            requestEntity,
//            String.class
//        );
//
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        String email = jsonNode.get("email").asText();
//
//        return email;
//    }
//}
