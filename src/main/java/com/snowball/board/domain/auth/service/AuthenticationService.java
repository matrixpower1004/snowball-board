package com.snowball.board.domain.auth.service;

import com.snowball.board.config.JwtService;
import com.snowball.board.domain.auth.dto.AuthenticationRequest;
import com.snowball.board.domain.auth.dto.AuthenticationResponse;
import com.snowball.board.domain.user.model.User;
import com.snowball.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    public String generateToken(User user) {
        return jwtService.generateToken(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserAccount(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUserAccount(request.getUserAccount()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
