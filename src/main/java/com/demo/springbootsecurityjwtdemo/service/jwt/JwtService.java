package com.demo.springbootsecurityjwtdemo.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Objects;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Objects> claims, UserDetails userDetails);
}
