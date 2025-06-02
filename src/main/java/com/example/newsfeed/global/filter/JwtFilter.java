package com.example.newsfeed.global.filter;

import com.example.newsfeed.global.common.Const;
import com.example.newsfeed.global.config.JwtUtil;
import com.example.newsfeed.global.dto.AuthUserDto;
import com.example.newsfeed.global.dto.ErrorResDto;
import com.example.newsfeed.user.service.BlackListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final BlackListService blackListService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String bearerJwt = request.getHeader(Const.AUTHORIZATION_HEADER);
        if (bearerJwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = jwtUtil.substringToken(bearerJwt);
        if (handleBlackList(jwt, response)) return;
        try {
            if (handleInvalidClaims(jwt, response)) return;
            setAuthentication(jwt);
            filterChain.doFilter(request, response);

        } catch (SecurityException | MalformedJwtException e) {
            sendResponse(HttpStatus.UNAUTHORIZED, response, "유효하지 않는 JWT 서명입니다.", e.getMessage());
        } catch (ExpiredJwtException e) {
            sendResponse(HttpStatus.UNAUTHORIZED, response, "만료된 JWT 토큰입니다.", e.getMessage());
        } catch (UnsupportedJwtException e) {
            sendResponse(HttpStatus.BAD_REQUEST, response, "지원되지 않는 JWT 토큰입니다.", e.getMessage());
        } catch (Exception e) {
            sendResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, "Internal server error", e.getMessage());
        }
    }

    private boolean handleBlackList(String jwt, HttpServletResponse response) throws IOException {
        if (blackListService.isExistBlackList(jwt)) {
            sendResponse(HttpStatus.FORBIDDEN, response, "사용할 수 없는 JWT 토큰입니다.", "A token that is blacklisted.");
            return true;
        }
        return false;
    }

    private boolean handleInvalidClaims(String jwt, HttpServletResponse response) throws IOException {
        Claims claims = jwtUtil.extractClaims(jwt);
        if (claims == null) {
            sendResponse(HttpStatus.BAD_REQUEST, response, "잘못된 JWT 토큰입니다.", "An error occurred in the payload return process.");
            return true;
        }
        return false;
    }

    private void setAuthentication(String jwt) {
        Long userId = jwtUtil.getUserIdFromToken(jwt);
        String name = jwtUtil.getNameFromToken(jwt);

        AuthUserDto authUser = new AuthUserDto(userId, name);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authUser, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private void sendResponse(HttpStatus status, HttpServletResponse response, String message, String errorDetail) throws IOException {
        ErrorResDto errorResDto = new ErrorResDto(
                status.value(),
                status.getReasonPhrase(),
                message,
                errorDetail,
                LocalDateTime.now().toString()
        );
        String responseBody = new ObjectMapper().writeValueAsString(errorResDto);

        log.error("{}: {}", status.getReasonPhrase(), errorDetail);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

}