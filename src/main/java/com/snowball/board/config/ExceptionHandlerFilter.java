package com.snowball.board.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowball.board.common.exception.dto.ExceptionDto;
import com.snowball.board.common.exception.message.AuthExceptionMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException exception) {

            //토큰의 유효기간 만료
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getWriter(), ExceptionDto.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message(AuthExceptionMessage.TOKEN_VALID_TIME_EXPIRED.message())
                    .build());

        } catch (JwtException | IllegalArgumentException exception) {

            //유효하지 않은 토큰
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getWriter(), ExceptionDto.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message(AuthExceptionMessage.FAIL_TOKEN_CHECK.message())
                    .build());
        }
    }
}
