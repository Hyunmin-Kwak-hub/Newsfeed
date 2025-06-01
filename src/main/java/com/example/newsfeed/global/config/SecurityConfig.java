package com.example.newsfeed.global.config;

import com.example.newsfeed.global.exception.CustomAccessDeniedHandler;
import com.example.newsfeed.global.exception.CustomAuthenticationEntryPoint;
import com.example.newsfeed.global.filter.JwtFilter;
import com.example.newsfeed.user.service.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final BlackListService blackListService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        //user
                        .requestMatchers(HttpMethod.POST, "/api/users", "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users", "/api/users/*").permitAll()
                        //article
                        .requestMatchers(HttpMethod.GET, "/api/articles", "/api/*/articles", "/api/articles/*").permitAll()
                        //comment
                        .requestMatchers(HttpMethod.GET, "/api/articles/*/comments", "/api/articles/*/comments/*").permitAll()
                        //follows
                        .requestMatchers(HttpMethod.GET, "/api/*/follows").permitAll()
                        //likes
                        .requestMatchers(HttpMethod.GET, "/api/articles/*/likes", "/api/articles/*/comments/*/likes").permitAll()

                        .anyRequest().authenticated()
                )

                //JWT 검증 필터 등록
                .addFilterBefore(new JwtFilter(jwtUtil, blackListService), UsernamePasswordAuthenticationFilter.class)
                // 예외 처리 설정
                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint(customAuthenticationEntryPoint) // 인증 오류
                                .accessDeniedHandler(customAccessDeniedHandler) // 인가 오류
                )
                .build();
    }
}