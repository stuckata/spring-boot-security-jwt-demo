package com.demo.springbootsecurityjwtdemo.service.impl;

import com.demo.springbootsecurityjwtdemo.api.dto.LoginRequestDto;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import com.demo.springbootsecurityjwtdemo.service.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    
    @Override
    public String authenticate(LoginRequestDto dto) throws ApplicationException {
        return null;
    }

    @Override
    public String refreshToken() throws ApplicationException {
        return null;
    }
}
