package com.demo.springbootsecurityjwtdemo.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.regex.Pattern;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    AES_ENCRYPTION_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "AES encryption exception"),
    AES_DECRYPTION_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "AES decryption exception"),
    CLIENT_NOT_VALID(HttpStatus.BAD_REQUEST, "Invalid client"),
    EMAIL_NOT_VALID(HttpStatus.BAD_REQUEST, "Invalid email address"),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "Email already exists"),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "Wrong password"),
    PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, "Password is not valid. Use at least {} characters - at least 1 uppercase letter, 1 lowercase letter and 1 digit."),
    PASSWORD_TOO_SHORT(HttpStatus.BAD_REQUEST, "Password should be at least {} characters."),
    PASSWORD_TOO_LONG(HttpStatus.BAD_REQUEST, "Password should be maximum {} characters."),
    PHONE_NOT_VALID(HttpStatus.BAD_REQUEST, "Invalid phone number"),
    USER_ACCOUNT_NOT_ACTIVE(HttpStatus.FORBIDDEN, "User account is deactivated."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    ;
    private final HttpStatus httpStatus;
    private final String description;

    @Override
    public String toString() {
        return this.name() + ":" + this.description;
    }

    public String format(String[] args) {
        if (args == null || args.length == 0) {
            return this.description;
        }
        String formatted = this.description;
        for (String arg : args) {
            formatted = formatted.replaceFirst(Pattern.quote("{}"), arg);
        }
        return formatted;
    }
}
