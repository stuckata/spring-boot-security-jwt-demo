package com.demo.springbootsecurityjwtdemo.service.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.expirationTimeInSeconds}")
    private int jwtExpirationTimeInSeconds;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    private final JwtEncoder encoder;

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(Collections.emptyMap(), userDetails);
    }

    @Override
    public String generateToken(Map<String, Objects> additionalClaims, UserDetails userDetails) {
        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer(this.jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(this.jwtExpirationTimeInSeconds))
                .subject(userDetails.getUsername())
                .claim("roles", roles);
        additionalClaims.forEach(claimsBuilder::claim);
        JwtClaimsSet claims = claimsBuilder.build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
