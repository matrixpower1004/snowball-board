package com.snowball.board.config.handler;

import com.snowball.board.common.exception.message.AuthExceptionMessage;
import com.snowball.board.domain.auth.service.CustomUserDetailsService;
import com.snowball.board.domain.user.model.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CustomAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    @Resource
    private final CustomUserDetailsService userDetailsService;
    public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userAccount = token.getName();
        String password = (String) token.getCredentials();

        User user = (User) userDetailsService.loadUserByUsername(userAccount);
        if (user.getPassword().equals(password)) {
            throw new BadCredentialsException(AuthExceptionMessage.MISMATCH_PASSWORD.message());
        }
        // set credentials null
        // credentials will remove by spring
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
