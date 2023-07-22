
package com.snowball.board.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowball.board.common.util.JwtUtil;
import com.snowball.board.domain.auth.service.AuthenticationService;
import com.snowball.board.domain.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;
    @Autowired
    public AuthSuccessHandler(AuthenticationService authenticationService, JwtUtil jwtUtil) {
        this.authenticationService = authenticationService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        authenticationService.saveRefreshToken(user, refreshToken);
        response.addCookie(generateHttponlyCookie(refreshToken));
        response.setStatus(SC_OK);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("access_token", accessToken);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

    private Cookie generateHttponlyCookie(String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
