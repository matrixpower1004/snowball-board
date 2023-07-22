package com.snowball.board.config;

import com.snowball.board.config.filter.AuthenticationFilter;
import com.snowball.board.config.filter.AuthorizationFilter;
import com.snowball.board.config.handler.AuthFailureHandler;
import com.snowball.board.config.handler.AuthSuccessHandler;
import com.snowball.board.config.handler.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthSuccessHandler loginSuccessHandler;
    private final AuthFailureHandler loginFailureHandler;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AuthorizationFilter authorizationFilter;
    @Autowired
    public WebSecurityConfig(AuthSuccessHandler loginSuccessHandler, AuthFailureHandler loginFailureHandler, CustomAuthenticationProvider customAuthenticationProvider, AuthorizationFilter authorizationFilter) {
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception  {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/api/user/check-email", "/api/user/check-nickName", "/api/auth/register", "/api/auth/refresh-token").permitAll()
                .antMatchers("/js/**", "/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authorizationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //.addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .headers().frameOptions().disable();

        return http.build();

                        // TODO: 2023-07-17 uncomment after impl functions finished
                /*
                        // 관리자 모든 엑세스 권한
                        .antMatchers("/**").hasRole(UserRole.ADMIN.name())

                        // GET 회원 관련 기능 엑세스 권한 (회원 정보 조회)
                        .antMatchers(GET, "/api/user/{id}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name())

                        // PATCH 회원 관련 기능 엑세스 권한 (회원 정보 수정, 등급 수정은 관리자 서버에서 구현), 현재 PATCH만 사용
                        .antMatchers(PATCH, "/api/user/{id}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
                        .antMatchers(PATCH, "/api/user/{id}/password").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())

                        // GET 게시글 관련 기능 엑세스 권한 (게시글 조회)
                        .antMatchers(GET, "/api/posts/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())

                        // POST 게시글 및 댓글 관련 기능 엑세스 권한 (게시글 및 댓글 작성)
                        .antMatchers(POST, "/api/posts/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
                        .antMatchers(POST, "/api/posts/{postId}/comments").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())

                        // DELTE 게시글 및 댓글 관련 기능 엑세스 권한 (게시글 및 댓글 삭제)
                        .antMatchers(DELETE, "/api/posts/{postId}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
                        .antMatchers(DELETE, "/api/comments/{commentId}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())

                        // PUT 게시글 및 댓글 관련 엑세스 권한 (게시글 및 댓글 수정)
                        .antMatchers(PUT, "/api/posts/{postId}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
                        .antMatchers(PUT, "/api/comments/{commentId}").hasAnyRole(UserRole.ADMIN.name(), UserRole.BEGINNER.name(), UserRole.EXPERT.name())
                */
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider);
    }

    @Bean
    public AuthenticationFilter authenticationFilter() {
        AuthenticationFilter customAuthenticationFilter = new AuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth/authenticate");     // 접근 URL
        customAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);    // '인증' 성공 시 해당 핸들러로 처리를 전가한다.
        customAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);    // '인증' 실패 시 해당 핸들러로 처리를 전가한다.
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

}
