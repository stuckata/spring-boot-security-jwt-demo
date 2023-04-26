package com.demo.springbootsecurityjwtdemo.service.encryption;

import com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode;
import com.demo.springbootsecurityjwtdemo.exception.ApplicationException;
import com.demo.springbootsecurityjwtdemo.service.encryption.EncryptionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <a href="https://www.javainterviewpoint.com/java-aes-256-gcm-encryption-and-decryption/">AES</a>
 */
@Slf4j
@Service
public class EncryptionServiceAesImpl implements EncryptionService {
    private static final String ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;

    @Value("${encryption.initializationVector}")
    private String encryptionInitVector;

    @Override
    public String encrypt(String original, String secretKey) throws ApplicationException {
        if (StringUtils.isBlank(original)) {
            return null;
        }
        try {
            IvParameterSpec iv = new IvParameterSpec(this.encryptionInitVector.getBytes(StandardCharsets.UTF_8));

            // Create SecretKeySpec
            SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv.getIV());

            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

            // Perform Encryption
            byte[] cipherText = cipher.doFinal(original.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(cipherText);

        } catch (Exception e) {
            log.error("Encryption problem", e);
            throw new ApplicationException(ErrorCode.AES_ENCRYPTION_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public String decrypt(String encrypted, String secretKey) throws ApplicationException {
        if (StringUtils.isBlank(encrypted)) {
            return null;
        }

        try {
            IvParameterSpec iv = new IvParameterSpec(this.encryptionInitVector.getBytes(StandardCharsets.UTF_8));

            // Create SecretKeySpec
            SecretKeySpec keySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);

            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv.getIV());

            // Get Cipher Instance
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);

            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

            // Perform Decryption
            byte[] decryptedText = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(decryptedText);
        } catch (Exception e) {
            log.error("Decryption problem", e);
            throw new ApplicationException(ErrorCode.AES_DECRYPTION_EXCEPTION, e.getMessage());
        }
    }
}
