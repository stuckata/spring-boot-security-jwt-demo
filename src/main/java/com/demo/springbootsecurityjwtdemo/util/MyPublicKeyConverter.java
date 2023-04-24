package com.demo.springbootsecurityjwtdemo.util;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.security.interfaces.RSAPublicKey;

@Component
@ConfigurationPropertiesBinding
public class MyPublicKeyConverter implements Converter<String, RSAPublicKey> {
    @Override
    public RSAPublicKey convert(String from) {
        return RsaKeyConverters.x509().convert(new ByteArrayInputStream(from.getBytes()));
    }
}