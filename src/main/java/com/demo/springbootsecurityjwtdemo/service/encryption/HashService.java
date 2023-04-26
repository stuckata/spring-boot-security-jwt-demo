package com.demo.springbootsecurityjwtdemo.service.encryption;

public interface HashService {

    String hashText(String plainStr, String salt);

    boolean matchHash(String plainStr, String hash);
}
