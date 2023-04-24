package com.demo.springbootsecurityjwtdemo.service;


import com.demo.springbootsecurityjwtdemo.api.dto.LoginRequestDto;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;

public interface AuthenticationService {
    String authenticate(LoginRequestDto dto) throws ApplicationException;

    String refreshToken() throws ApplicationException;
}
