package com.backend.hanghaew7t4clone.jwt;

import com.backend.hanghaew7t4clone.exception.ErrorCode;
import com.backend.hanghaew7t4clone.shared.Message;
import com.backend.hanghaew7t4clone.shared.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointException implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
                new ObjectMapper().writeValueAsString(
                new ResponseEntity<>(Message.fail("BAD_REQUEST", String.valueOf(ErrorCode.UNAUTHORIZED)), HttpStatus.BAD_REQUEST)
                )
        );
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }

}
