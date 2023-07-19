package com.snowball.board.domain.auth.service;

import com.snowball.board.common.exception.message.AuthExceptionMessage;
import com.snowball.board.common.exception.model.UnauthorizedException;
import com.snowball.board.config.JwtService;
import com.snowball.board.domain.auth.dto.AuthenticationRequest;
import com.snowball.board.domain.auth.dto.AuthenticationResponse;
import com.snowball.board.domain.auth.dto.LogoutResponse;
import com.snowball.board.domain.token.model.Token;
import com.snowball.board.domain.token.repository.TokenRepository;
import com.snowball.board.domain.user.dto.RegisterRequest;
import com.snowball.board.domain.user.model.User;
import com.snowball.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Register User
     * @param request
     * @return AuthenticationResponse include access,refresh token
     */
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .userAccount(request.getUserAccount())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nickName(request.getNickName())
                .build();
        validateUserAccountDuplicate(user.getUserAccount());
        userRepository.save(user);
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        saveRefreshToken(user, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Validate Duplicate UserAccount(Login ID) exists
     * @param userAccount
     * throws IllegalStateException
     */
    private void validateUserAccountDuplicate(String userAccount){
        Optional<User> findUser = userRepository.findByUserAccount(userAccount);
        if (findUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
    }

    /**
     * Create new token, refresh_token if authenticate success
     * RevokeAll exist tokens
     *
     * @param request
     * @return AuthenticationResponse include access,refresh token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserAccount(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUserAccount(request.getUserAccount()).orElseThrow();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveRefreshToken(user, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Logout
     * @param request
     * @return LogoutResponse
     */
    public LogoutResponse logout(AuthenticationRequest request) {
        User user = userRepository.findByUserAccount(request.getUserAccount()).orElseThrow();
        revokeAllUserTokens(user);
        return LogoutResponse.builder()
                .message("성공적으로 로그아웃 되었습니다.")
                .build();
    }

    private void saveRefreshToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType("BEARER")
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    /**
     * Revoke Tokens user have
     * When Logout or New Login
     * @param user
     */
    private void revokeAllUserTokens(User user) {
        List<Token> validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> {
            token.setRevoked(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }

    public AuthenticationResponse refreshAccessToken(HttpServletRequest request) throws IOException {
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies);
        String refreshToken = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh_token")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken != null) {
            String userAccount = jwtService.extractUserAccount(refreshToken);

            User user = userRepository.findByUserAccount(userAccount)
                    .orElseThrow(() -> new UnauthorizedException(AuthExceptionMessage.FAIL_TOKEN_CHECK.message()));

            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);

                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
            }
        }
        throw new AuthenticationException(AuthExceptionMessage.FAIL_TOKEN_CHECK.message());

    }

    public String generateCookie(String token) {
        return jwtService.generateCookie(token).toString();
    }

}
