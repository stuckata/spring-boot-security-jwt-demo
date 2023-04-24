package com.demo.springbootsecurityjwtdemo.entity;

import lombok.Getter;

public enum UserRoleEntity {
    USER(1),
    ADMIN(2);
    @Getter
    private final int code;

    UserRoleEntity(int code) {
        this.code = code;
    }
}
