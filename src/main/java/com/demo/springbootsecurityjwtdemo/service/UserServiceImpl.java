package com.demo.springbootsecurityjwtdemo.service;

import com.demo.springbootsecurityjwtdemo.api.dto.CreateAccountRequestDto;
import com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode;
import com.demo.springbootsecurityjwtdemo.api.dto.LoginRequestDto;
import com.demo.springbootsecurityjwtdemo.entity.UserEntity;
import com.demo.springbootsecurityjwtdemo.entity.UserRoleEntity;
import com.demo.springbootsecurityjwtdemo.entity.UserStatusEntity;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import com.demo.springbootsecurityjwtdemo.repository.UserRepository;
import com.demo.springbootsecurityjwtdemo.service.encryption.EmailEncryptionService;
import com.demo.springbootsecurityjwtdemo.service.encryption.HashService;
import com.demo.springbootsecurityjwtdemo.service.validation.PasswordValidationService;
import com.demo.springbootsecurityjwtdemo.service.validation.UsernameValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UsernameValidationService usernameValidationService;
    private final PasswordValidationService passwordValidationService;
    private final UserRepository userRepository;
    private final EmailEncryptionService emailEncryptionService;
    private final HashService hashService;

    @Override
    public UserEntity createUser(CreateAccountRequestDto dto) throws ApplicationException {
        this.usernameValidationService.validate(dto.getEmail());
        this.passwordValidationService.validate(dto.getPassword());

        LocalDate now = LocalDate.now();
        String encryptedMail = this.emailEncryptionService.encrypt(dto.getEmail());
        String hashedPassword = createHash(dto.getPassword(), now);

        UserEntity user = UserEntity.builder()
                .status(UserStatusEntity.ACTIVE)
                .createdOn(now)
                .email(encryptedMail)
                .password(hashedPassword)
                .role(UserRoleEntity.USER)
                .build();
        return this.userRepository.save(user);
    }

    @Override
    public UserEntity findUserByUsernameAndPassword(LoginRequestDto dto) throws ApplicationException {
        UserEntity user = findUserByEmail(dto.getEmail());
        if (user == null) {
            throw new ApplicationException(ErrorCode.USER_NOT_FOUND);
        }

        boolean passwordMatch = this.hashService.matchHash(dto.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new ApplicationException(ErrorCode.PASSWORD_MISMATCH);
        }
        return user;
    }

    @Override
    public UserEntity findUserByEmail(String email) throws ApplicationException {
        String encryptedUsername = this.emailEncryptionService.encrypt(email);
        Optional<UserEntity> existingUser = this.userRepository.findByEmail(encryptedUsername);
        return existingUser.orElse(null);
    }

    private String createHash(String plainPassword, LocalDate createdOn) {
        return this.hashService.hashText(plainPassword, createPasswordSalt(createdOn));
    }

    private String createPasswordSalt(LocalDate createDate) {
        // Write some unique salt algorithm using whatever you want.
        // Note that the algorithm must always return the same value for the same user.
        LocalDate salt = createDate
                .plusWeeks(createDate.getDayOfMonth())
                .minusDays(createDate.getMonthValue())
                .plusMonths(createDate.getMonthValue());
        return new String(Base64.getEncoder().encode(salt.toString()
                .getBytes(StandardCharsets.UTF_8)));
    }
}
