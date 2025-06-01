package com.example.newsfeed.global.filter;

import com.example.newsfeed.global.config.JwtUtil;
import com.example.newsfeed.global.dto.AuthUserDto;
import com.example.newsfeed.global.exception.CustomAccessDeniedHandler;
import com.example.newsfeed.user.service.BlackListService;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j(topic = "JwtFilter")
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final BlackListService blackListService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String bearerJwt = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerJwt == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = jwtUtil.substringToken(bearerJwt);
        if (blackListService.isExistBlackList(jwt)) {
            log.error("BAD_REQUEST: 사용할 수 없는 JWT 토큰입니다.");
            customAccessDeniedHandler.handle(request, response, new AccessDeniedException("사용할 수 없는 JWT 토큰입니다."));
            return;
        }
        try {
            checkClaims(jwt, response);
            setAuthentication(jwt);
            filterChain.doFilter(request, response);

        } catch (SecurityException | MalformedJwtException e) {
            log.error("UNAUTHORIZED: 유효하지 않는 JWT 서명입니다.", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("UNAUTHORIZED: 만료된 JWT 토큰입니다.", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("BAD_REQUEST: 지원되지 않는 JWT 토큰입니다.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
        } catch (Exception e) {
            log.error("Internal server error", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void checkClaims(String jwt, HttpServletResponse response) throws IOException {
        Claims claims = jwtUtil.extractClaims(jwt);
        if (claims == null) {
            log.error("BAD_REQUEST: 잘못된 JWT 토큰입니다");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");
        }
    }

    private void setAuthentication(String jwt) {
        Long userId = jwtUtil.getUserIdFromToken(jwt);
        String name = jwtUtil.getNameFromToken(jwt);

        AuthUserDto authUser = new AuthUserDto(userId, name);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authUser, null, Collections.emptyList());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}