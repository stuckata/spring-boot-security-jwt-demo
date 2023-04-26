package com.demo.springbootsecurityjwtdemo.service.encryption;

import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailEncryptionServiceImpl implements EmailEncryptionService {

    @Value("${encryption.secret.email}")
    private String emailEncryptionSecret;

    private final EncryptionService encryptionService;

    @Override
    public String encrypt(String email) throws ApplicationException {
        return this.encryptionService.encrypt(email, this.emailEncryptionSecret);
    }

    @Override
    public String decrypt(String encryptedEmail) throws ApplicationException {
        return this.encryptionService.decrypt(encryptedEmail, this.emailEncryptionSecret);
    }
}
