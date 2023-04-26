package com.demo.springbootsecurityjwtdemo.controller;

import com.demo.springbootsecurityjwtdemo.api.AuthenticationApi;
import com.demo.springbootsecurityjwtdemo.api.dto.CreateAccountRequestDto;
import com.demo.springbootsecurityjwtdemo.api.dto.LoginRequestDto;
import com.demo.springbootsecurityjwtdemo.api.dto.TokenResponseDto;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import com.demo.springbootsecurityjwtdemo.service.AuthenticationService;
import com.demo.springbootsecurityjwtdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController implements AuthenticationApi {

    private static final String X_AUTH_TOKEN = "X-AUTH-TOKEN";

    @Value("${jwt.expirationTimeInSeconds}")
    private int jwtExpirationTimeInSeconds;

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final JwtDecoder jwtDecoder;

    @Override
    public ResponseEntity<Void> createAccount(String clientId, CreateAccountRequestDto request) throws ApplicationException {
        this.userService.createUser(request);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<TokenResponseDto> login(String clientId, LoginRequestDto request) throws ApplicationException {
        String token = this.authenticationService.authenticate(request);
        return buildTokenResponseDto(token);
    }

    @Override
    public ResponseEntity<TokenResponseDto> refreshToken(String clientId) throws ApplicationException {
        String token = this.authenticationService.refreshToken();
        return buildTokenResponseDto(token);
    }

    private ResponseEntity<TokenResponseDto> buildTokenResponseDto(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(X_AUTH_TOKEN, token); // optional (you can return it as header OR in the body)

        TokenResponseDto tokenDto = TokenResponseDto.builder()
                .token(token)
                .expiresInSec(this.jwtExpirationTimeInSeconds)
                .expirationTimeUtc(getTokenExpirationTimeUtc(token))
                .build();

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(tokenDto);
    }

    private LocalDateTime getTokenExpirationTimeUtc(String token) {
        LocalDateTime expirationTimeUtc = null;
        Jwt jwt = this.jwtDecoder.decode(token);
        Instant expiresAt = jwt.getExpiresAt();
        if (expiresAt != null) {
            expirationTimeUtc = LocalDateTime.ofInstant(expiresAt, ZoneOffset.UTC);
        }
        return expirationTimeUtc;
    }
}
