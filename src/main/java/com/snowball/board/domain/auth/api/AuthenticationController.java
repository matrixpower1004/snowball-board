package com.snowball.board.domain.auth.api;

import com.snowball.board.domain.auth.dto.AuthenticationRequest;
import com.snowball.board.domain.auth.dto.AuthenticationResponse;
import com.snowball.board.domain.auth.service.AuthenticationService;
import com.snowball.board.domain.user.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        response.addCookie(generateHttponlyCookie(authenticationResponse.getRefreshToken()));
        //set refresh token null to response, only set in httpOnly secure cookie
        authenticationResponse.setRefreshToken(null);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest userRegisterRequest, HttpServletResponse response) {
        AuthenticationResponse authenticationResponse = authenticationService.register(userRegisterRequest);
        response.addCookie(generateHttponlyCookie(authenticationResponse.getRefreshToken()));
        //set refresh token null to response, only set in httpOnly secure cookie
        authenticationResponse.setRefreshToken(null);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    // Use HttpOnly Secure Cookie to refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token") HttpServletRequest request) throws IOException {

        return new ResponseEntity<>(authenticationService.refreshAccessToken(request), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        return new ResponseEntity<>(authenticationService.logout(authenticationRequest), HttpStatus.OK);
    }

    private Cookie generateHttponlyCookie(String refreshToken) {
        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}
