package com.snowball.board.config.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowball.board.common.exception.message.AuthExceptionMessage;
import com.snowball.board.common.exception.model.UnauthorizedException;
import com.snowball.board.domain.auth.dto.AuthenticationRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        try {
            authRequest = generateAuthenticationToken(request);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (Exception exception) {
            throw new UnauthorizedException(AuthExceptionMessage.NOT_AUTHORIZED_ACCESS.message());
        }
    }

    private UsernamePasswordAuthenticationToken generateAuthenticationToken(HttpServletRequest request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            AuthenticationRequest authenticationRequest = objectMapper.readValue(request.getInputStream(), AuthenticationRequest.class);
            return new UsernamePasswordAuthenticationToken(authenticationRequest.getUserAccount(), authenticationRequest.getPassword());
        } catch (UsernameNotFoundException ae) {
            throw new UsernameNotFoundException(ae.getMessage());
        } catch (Exception e) {
            throw new IllegalStateException(AuthExceptionMessage.NOT_AUTHORIZED_ACCESS.message());
        }

    }

}
