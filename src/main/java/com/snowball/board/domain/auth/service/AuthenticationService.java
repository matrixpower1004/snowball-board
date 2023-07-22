package com.snowball.board.domain.auth.service;

import com.snowball.board.common.exception.message.AuthExceptionMessage;
import com.snowball.board.common.exception.message.ExceptionMessage;
import com.snowball.board.common.exception.model.UnauthorizedException;
import com.snowball.board.common.util.UserRole;
import com.snowball.board.common.util.JwtUtil;
import com.snowball.board.domain.auth.dto.AuthenticationRequest;
import com.snowball.board.domain.auth.dto.AuthenticationResponse;
import com.snowball.board.domain.auth.dto.LogoutResponse;
import com.snowball.board.domain.token.model.Token;
import com.snowball.board.domain.token.repository.TokenRepository;
import com.snowball.board.domain.user.dto.RegisterRequest;
import com.snowball.board.domain.user.model.User;
import com.snowball.board.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Autowired
    public AuthenticationService(UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

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
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .userRole(UserRole.BEGINNER)
                .userStatus(true)
                .build();
        validateUserAccountDuplicate(user.getUserAccount());
        userRepository.save(user);
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
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
            throw new IllegalStateException(ExceptionMessage.ALREADY_EXISTS_USER.message());
        }
    }

    /**
     * Logout
     * @param request
     * @return LogoutResponse
     */
    public LogoutResponse logout(AuthenticationRequest request) {
        User user = userRepository.findByUserAccount(request.getUserAccount()).orElseThrow((() -> new IllegalStateException(AuthExceptionMessage.USER_NOT_FOUND.message())));
        revokeAllUserTokens(user);
        return LogoutResponse.builder()
                .message("성공적으로 로그아웃 되었습니다.")
                .build();
    }

    public void saveRefreshToken(User user, String jwtToken) {
        //revoke before save new refresh token
        revokeAllUserTokens(user);
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

    public AuthenticationResponse refreshAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
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
            String userAccount = jwtUtil.extractUserAccount(refreshToken);

            User user = userRepository.findByUserAccount(userAccount)
                    .orElseThrow(() -> new UnauthorizedException(AuthExceptionMessage.FAIL_TOKEN_CHECK.message()));

            if (jwtUtil.isValidToken(refreshToken)) {
                String accessToken = jwtUtil.generateToken(user);

                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .build();
            }
        }
        throw new UnauthorizedException(AuthExceptionMessage.FAIL_TOKEN_CHECK.message());

    }

}
