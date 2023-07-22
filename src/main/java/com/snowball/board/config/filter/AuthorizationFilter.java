package com.snowball.board.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowball.board.common.exception.dto.ExceptionDto;
import com.snowball.board.common.exception.message.AuthExceptionMessage;
import com.snowball.board.common.util.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        List<String> ignored = Arrays.asList(
                "/login",
                "/register",
                "/resources/**",
                "/js/**",
                "/api/user/check-email",
                "/api/user/check-nickName",
                "/api/auth/register",
                "/api/auth/refresh-token"
        );
        if (ignored.stream().anyMatch(pattern -> antPathMatcher.match(pattern, request.getRequestURI()))) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        final String userAccount;


        // Header에 Token이 존재 하지 않거나 비정상 적인 type
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ExceptionDto errorResponse = ExceptionDto.builder()
                    .message(AuthExceptionMessage.TOKEN_NOT_FOUND.message())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
            return;
        }
        //Token이 존재 할 경우
        //"Bearer " -> 6 length
        accessToken = authHeader.substring(7);
        userAccount = jwtUtil.extractUserAccount(accessToken);
        if (userAccount != null) {
            try {
                //검증 완료
                if (jwtUtil.isValidToken(accessToken)) {
                    //Get Authentication From decoding accessToken
                    Authentication authentication = jwtUtil.getAuthenticationParseToken(accessToken);
                    //Set Authentication to SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                }
            } catch (Exception exception) {
                response.setStatus(SC_UNAUTHORIZED);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                ExceptionDto errorResponse = ExceptionDto.builder()
                        .message(exception.getMessage())
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
            }
        }
    }
}
