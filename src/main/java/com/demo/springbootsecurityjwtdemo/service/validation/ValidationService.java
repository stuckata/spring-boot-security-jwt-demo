package com.demo.springbootsecurityjwtdemo.service.validation;

import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;

public interface ValidationService {
    void validate(String str) throws ApplicationException;
}
