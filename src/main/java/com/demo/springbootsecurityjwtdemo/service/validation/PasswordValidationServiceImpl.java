package com.demo.springbootsecurityjwtdemo.service.validation;


import com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class PasswordValidationServiceImpl implements PasswordValidationService {

    @Value("${user.password.regex}")
    private String pwdRegex;

    @Value("${user.password.minLength}")
    private Integer pwdMinLength;

    @Value("${user.password.maxLength}")
    private Integer pwdMaxLength;

    @Override
    public void validate(String plainPassword) throws ApplicationException {
        if (StringUtils.isBlank(plainPassword) || plainPassword.length() < this.pwdMinLength) {
            throw new ApplicationException(ErrorCode.PASSWORD_TOO_SHORT, String.valueOf(this.pwdMinLength));
        } else if (plainPassword.length() > this.pwdMaxLength) {
            throw new ApplicationException(ErrorCode.PASSWORD_TOO_LONG, String.valueOf(this.pwdMaxLength));
        }

        boolean valid = Pattern.matches(this.pwdRegex, plainPassword);
        if (!valid) {
            throw new ApplicationException(ErrorCode.PASSWORD_NOT_VALID, String.valueOf(this.pwdMinLength));
        }
    }
}
