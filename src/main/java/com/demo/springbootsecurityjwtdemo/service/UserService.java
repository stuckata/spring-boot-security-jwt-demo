package com.demo.springbootsecurityjwtdemo.service;


import com.demo.springbootsecurityjwtdemo.api.dto.CreateAccountRequestDto;
import com.demo.springbootsecurityjwtdemo.api.dto.LoginRequestDto;
import com.demo.springbootsecurityjwtdemo.entity.UserEntity;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;

public interface UserService {
    UserEntity createUser(CreateAccountRequestDto dto) throws ApplicationException;

    UserEntity findUserByUsernameAndPassword(LoginRequestDto dto) throws ApplicationException;

    UserEntity findUserByEmail(String email) throws ApplicationException;
}
