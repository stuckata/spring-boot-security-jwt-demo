package com.demo.springbootsecurityjwtdemo.service.validation;

import com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode;
import com.demo.springbootsecurityjwtdemo.entity.UserEntity;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import com.demo.springbootsecurityjwtdemo.repository.UserRepository;
import com.demo.springbootsecurityjwtdemo.service.encryption.EncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsernameValidationServiceImpl implements UsernameValidationService {

    @Value("${encryption.secret.email}")
    private String emailEncryptionSecret;

    private final EncryptionService encryptionService;
    private final UserRepository userRepository;
    private final EmailValidationService emailValidationService;

    @Override
    public void validate(String username) throws ApplicationException {
        UserEntity existingUser = findUserByUsername(username);
        if (existingUser != null) {
            throw new ApplicationException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        this.emailValidationService.validate(username);
    }

    private UserEntity findUserByUsername(String username) throws ApplicationException {
        String encryptedUsername = this.encryptionService.encrypt(username, this.emailEncryptionSecret);
        Optional<UserEntity> existingUser = this.userRepository.findByEmail(encryptedUsername);
        return existingUser.orElse(null);
    }
}
