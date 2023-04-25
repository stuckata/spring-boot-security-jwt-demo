package com.demo.springbootsecurityjwtdemo.service.encryption;

import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;

public interface EncryptionService {

    String encrypt(String original, String secretKey) throws ApplicationException;

    String decrypt(String encrypted, String secretKey) throws ApplicationException;
}
