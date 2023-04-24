package com.demo.springbootsecurityjwtdemo.entity;

import lombok.Getter;

public enum UserStatusEntity {
    ACTIVE(1),
    TERMINATED(2);
    @Getter
    private final int code;

    UserStatusEntity(int code) {
        this.code = code;
    }
}
