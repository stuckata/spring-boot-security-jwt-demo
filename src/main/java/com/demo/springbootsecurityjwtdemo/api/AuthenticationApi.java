package com.demo.springbootsecurityjwtdemo.api;

import com.demo.springbootsecurityjwtdemo.api.dto.*;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthenticationApi {

    String CLIENT_ID_HEADER = "Client-ID";

    @Operation(
            summary = "Spring JWT Demo: Create Account/ Sign Up (Public Endpoint)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Service Unavailable",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )
    @PostMapping(
            path = "/account/create",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    ResponseEntity<Void> createAccount(
            @RequestHeader(value = CLIENT_ID_HEADER) String clientId,
            @RequestBody CreateAccountRequestDto request
    ) throws ApplicationException;

    @Operation(
            summary = "Spring JWT Demo: Login/ Sign In (Public Endpoint)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(schema = @Schema(implementation = TokenResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Object Not Found",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Service Unavailable",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )
    @PostMapping(
            path = "/login",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    ResponseEntity<TokenResponseDto> login(
            @RequestHeader(value = CLIENT_ID_HEADER) String clientId,
            @RequestBody LoginRequestDto request
    ) throws ApplicationException;

    @Operation(
            summary = "Spring JWT Demo: Refresh Token (Protected Endpoint)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful response",
                            content = @Content(schema = @Schema(implementation = TokenResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "503",
                            description = "Service Unavailable",
                            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                    )
            }
    )
    @PostMapping(
            path = "/token/refresh"
    )
    @SecurityRequirement(name = "bearerAuth")
    ResponseEntity<TokenResponseDto> refreshToken(
            @RequestHeader(value = CLIENT_ID_HEADER) String clientId
    ) throws ApplicationException;
}
