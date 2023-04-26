package com.demo.springbootsecurityjwtdemo.service.validation;

import com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailValidationServiceImpl implements EmailValidationService {

    @Value("${user.email.regex}")
    private String emailRegex;

    @Override
    public void validate(String email) throws ApplicationException {
        boolean isValid = Pattern.matches(this.emailRegex, email);
        if (!isValid) {
            throw new ApplicationException(ErrorCode.EMAIL_NOT_VALID);
        }
    }
}
