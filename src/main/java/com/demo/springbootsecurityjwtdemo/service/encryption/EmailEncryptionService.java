package com.demo.springbootsecurityjwtdemo.service.encryption;

import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;

public interface EmailEncryptionService {

    String encrypt(String email) throws ApplicationException;

    String decrypt(String encryptedEmail) throws ApplicationException;
}
