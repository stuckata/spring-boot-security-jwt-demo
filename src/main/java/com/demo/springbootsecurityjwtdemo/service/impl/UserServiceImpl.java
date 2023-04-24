package com.demo.springbootsecurityjwtdemo.service.impl;

import com.demo.springbootsecurityjwtdemo.api.dto.CreateAccountRequestDto;
import com.demo.springbootsecurityjwtdemo.api.dto.LoginRequestDto;
import com.demo.springbootsecurityjwtdemo.entity.UserEntity;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import com.demo.springbootsecurityjwtdemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserEntity createUser(CreateAccountRequestDto dto) throws ApplicationException {
        return null;
    }

    @Override
    public UserEntity findUserByUsernameAndPassword(LoginRequestDto dto) throws ApplicationException {
        return null;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws ApplicationException {
        return null;
    }
}
