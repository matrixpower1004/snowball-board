//package com.snowball.board.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableMethodSecurity
//public class SecurityConfiguration {
//
//    private final AuthorizationFilter authenticationFilter;
//    private final ExceptionHandlerFilter exceptionHandlerFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
//
//                // 제한 없이 접근 가능, 전역설정
//                .antMatchers(
//                        "/login",
//                        "/register",
//                        "/api/user/longin",
//                        "/api/user/register",
//                        "/api/user/check-nickName",
//                        "/api/auth/**",
//                        "/**"
//                ).permitAll()
//                // TODO: 2023-07-17 uncomment after jwtSetting
///*
//                // 관리자 모든 엑세스 권한
//                .antMatchers("/**").hasRole(UserRole.ADMIN.name())
//
//                // GET 회원 관련 기능 엑세스 권한 (회원 정보 조회)
//                .antMatchers(GET, "/api/user/{id}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name())
//
//                // PATCH 회원 관련 기능 엑세스 권한 (회원 정보 수정, 등급 수정은 관리자 서버에서 구현), 현재 PATCH만 사용
//                .antMatchers(PATCH, "/api/user/{id}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//                .antMatchers(PATCH, "/api/user/{id}/password").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//
//                // GET 게시글 관련 기능 엑세스 권한 (게시글 조회)
//                .antMatchers(GET, "/api/posts/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//
//                // POST 게시글 및 댓글 관련 기능 엑세스 권한 (게시글 및 댓글 작성)
//                .antMatchers(POST, "/api/posts/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//                .antMatchers(POST, "/api/posts/{postId}/comments").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//
//                // DELTE 게시글 및 댓글 관련 기능 엑세스 권한 (게시글 및 댓글 삭제)
//                .antMatchers(DELETE, "/api/posts/{postId}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//                .antMatchers(DELETE, "/api/comments/{comentId}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//
//                // PUT 게시글 및 댓글 관련 엑세스 권한 (게시글 및 댓글 수정)
//                .antMatchers(PUT, "/api/posts/{postId}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//                .antMatchers(PUT, "/api/comments/{commentId}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
//
//     */           //명시되지 않은 URL 인증 필요
//                .anyRequest()
//                .authenticated()
//                .and()
//                //Session x, STATELESS
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .logout()
//                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                //.logoutUrl()
//                //.logoutHandler()
//                //.formLogin().disable();
//                .and()
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(exceptionHandlerFilter, AuthorizationFilter.class);
//        http.headers().frameOptions().disable();
//
//        return http.build();
//    }
//}
