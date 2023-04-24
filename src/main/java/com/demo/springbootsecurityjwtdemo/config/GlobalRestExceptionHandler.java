package com.demo.springbootsecurityjwtdemo.config;

import com.demo.springbootsecurityjwtdemo.api.dto.ErrorResponseDto;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    protected ResponseEntity<ErrorResponseDto> handleApplicationException(ApplicationException ex) {
        return createErrorResponse(ex.getCode(), ex.getArgs());
    }

    private ResponseEntity<ErrorResponseDto> createErrorResponse(ErrorCode errCode, List<String> args) {
        ErrorResponseDto errDto = ErrorResponseDto.builder()
                .code(errCode.name())
                .description(errCode.getDescription())
                .args(args)
                .build();
        return ResponseEntity.status(errCode.getHttpStatus()).body(errDto);
    }
}
