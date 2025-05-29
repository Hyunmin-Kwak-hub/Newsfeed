package com.example.newsfeed.global.config;

import com.example.newsfeed.global.exception.CustomAccessDeniedHandler;
import com.example.newsfeed.global.exception.CustomAuthenticationEntryPoint;
import com.example.newsfeed.global.filter.JwtFilter;
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

import static com.example.newsfeed.global.common.ApiUrls.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
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
                        .requestMatchers(HttpMethod.POST, USER[0], USER[1]).permitAll()
                        .requestMatchers(HttpMethod.GET, USER[0], USER[2]).permitAll()

                        .requestMatchers(USER[0], USER[3]).authenticated()

                        //article
                        .requestMatchers(HttpMethod.GET, ARTICLE[0], ARTICLE[1],  ARTICLE[2]).permitAll()

                        .requestMatchers(ARTICLE[0], ARTICLE[2]).authenticated()

                        //comment
                        .requestMatchers(HttpMethod.GET, COMMENT[0], COMMENT[1]).permitAll()
                        .requestMatchers(COMMENT[0], COMMENT[1]).authenticated()

                        //follows
                        .requestMatchers(HttpMethod.GET, FOLLOW[0]).permitAll()

                        .requestMatchers(FOLLOW[0], FOLLOW[1], FOLLOW[2]).authenticated()

                        //likes
                        .requestMatchers(HttpMethod.GET, LIKE[0], LIKE[1]).permitAll()
                        .requestMatchers(LIKE[0], LIKE[1]).authenticated()

                        .anyRequest().denyAll() // 나머지 요청 거부
                )

                //JWT 검증 필터 등록
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // 예외 처리 설정
                .exceptionHandling(configurer ->
                        configurer
                                .authenticationEntryPoint(customAuthenticationEntryPoint) // 인증 오류
                                .accessDeniedHandler(customAccessDeniedHandler) // 인가 오류
                )
                .build();
    }
}