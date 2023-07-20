package com.snowball.board.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowball.board.common.exception.dto.ExceptionDto;
import com.snowball.board.common.exception.message.AuthExceptionMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth") || request.getServletPath().contains("/login") || request.getServletPath().contains("/register") || request.getServletPath().contains("/resources") || request.getServletPath().contains("/api/user/check-email") || request.getServletPath().contains("/api/user/check-nickName") || request.getServletPath().contains("/h2-console") || request.getServletPath().contains("/board")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        final String userAccount;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ExceptionDto errorResponse = ExceptionDto.builder()
                    .message(AuthExceptionMessage.TOKEN_NOT_FOUND.message())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build();
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            return;
        }

        //"Bearer " -> 7 length
        accessToken = authHeader.substring(7);
        userAccount = jwtService.extractUserAccount(accessToken);

        if (userAccount != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userAccount);

            if (jwtService.isTokenValid(accessToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(SC_UNAUTHORIZED);
                response.setContentType(APPLICATION_JSON_VALUE);
                response.setCharacterEncoding("utf-8");
                ExceptionDto errorResponse = ExceptionDto.builder()
                        .message(AuthExceptionMessage.FAIL_TOKEN_CHECK.message())
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build();
                new ObjectMapper().writeValue(response.getWriter(), errorResponse);
            }
        }
    }
}
