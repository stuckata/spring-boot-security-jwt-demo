package com.demo.springbootsecurityjwtdemo.service.encryption;


import org.bouncycastle.crypto.generators.OpenBSDBCrypt;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class HashServiceBCryptImpl implements HashService {
    @Override
    public String hashText(String plainStr, String salt) {
        return OpenBSDBCrypt.generate(plainStr.getBytes(StandardCharsets.UTF_8), salt.getBytes(), 5);
    }

    public boolean matchHash(String plainStr, String hash) {
        return OpenBSDBCrypt.checkPassword(hash, plainStr.getBytes(StandardCharsets.UTF_8));
    }
}
