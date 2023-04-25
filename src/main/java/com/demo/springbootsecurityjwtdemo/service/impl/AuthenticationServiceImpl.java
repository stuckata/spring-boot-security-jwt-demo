package com.demo.springbootsecurityjwtdemo.service.impl;

import com.demo.springbootsecurityjwtdemo.api.dto.LoginRequestDto;
import com.demo.springbootsecurityjwtdemo.entity.UserEntity;
import com.demo.springbootsecurityjwtdemo.entity.UserStatusEntity;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import com.demo.springbootsecurityjwtdemo.service.AuthenticationService;
import com.demo.springbootsecurityjwtdemo.service.jwt.JwtService;
import com.demo.springbootsecurityjwtdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode.USER_ACCOUNT_NOT_ACTIVE;

@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public String authenticate(LoginRequestDto dto) throws ApplicationException {
        UserEntity userEntity = this.userService.findUserByUsernameAndPassword(dto);
        return generateToken(userEntity, dto.getEmail());
    }

    @Override
    public String refreshToken() throws ApplicationException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = this.userService.findUserByEmail(email);
        return generateToken(userEntity, email);
    }

    private String generateToken(UserEntity userEntity, String email) throws ApplicationException {
        boolean isActive = UserStatusEntity.ACTIVE.equals(userEntity.getStatus());
        if (!isActive) {
            throw new ApplicationException(USER_ACCOUNT_NOT_ACTIVE);
        }
        UserDetails user = createUserDetails(userEntity, email);
        return this.jwtService.generateToken(user);
    }

    private static UserDetails createUserDetails(UserEntity userEntity, String email) {
        return new User(
                email,
                email,
                true,
                true,
                true,
                true,
                Set.of(new SimpleGrantedAuthority(userEntity.getRole().name()))
        );
    }
}
