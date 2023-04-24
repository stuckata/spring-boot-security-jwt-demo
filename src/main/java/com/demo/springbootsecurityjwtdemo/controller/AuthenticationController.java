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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        httpHeaders.set(X_AUTH_TOKEN, token); // optional

        LocalDateTime expirationTimeUtc = LocalDateTime.now(ZoneOffset.UTC);
        expirationTimeUtc = expirationTimeUtc.plusSeconds(this.jwtExpirationTimeInSeconds);

        TokenResponseDto tokenDto = TokenResponseDto.builder()
                .token(token)
                .expiresInSec(this.jwtExpirationTimeInSeconds)
                .expirationTimeUtc(expirationTimeUtc)
                .build();

        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(tokenDto);
    }
}
