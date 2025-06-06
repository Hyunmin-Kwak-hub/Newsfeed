package com.example.newsfeed.global.exception;

import com.example.newsfeed.global.dto.ErrorResDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        ErrorResDto errorResDto = new ErrorResDto(
                status.value(),
                status.getReasonPhrase(),
                "인증되지 않은 URL 요청입니다.",
                authException.getMessage(),
                LocalDateTime.now().toString()
        );
        String responseBody = objectMapper.writeValueAsString(errorResDto);

        log.error("UNAUTHORIZED: Not Authenticated Request - Uri : {}", request.getRequestURI());

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}