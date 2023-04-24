package com.demo.springbootsecurityjwtdemo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenResponseDto implements Serializable {
    @Schema(description = "The Token")
    private String token;

    @Schema(description = "Expires in X Seconds")
    private long expiresInSec;

    @Schema(description = "Expiration time (UTC)")
    private LocalDateTime expirationTimeUtc;
}
