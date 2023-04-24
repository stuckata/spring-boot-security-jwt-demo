package com.demo.springbootsecurityjwtdemo.exception;

import com.demo.springbootsecurityjwtdemo.api.dto.ErrorCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationException extends Exception {
    @Getter
    private final ErrorCode code;
    @Getter
    private final List<String> args = new ArrayList<>();

    public ApplicationException(ErrorCode code) {
        this(code, "");
    }

    public ApplicationException(ErrorCode code, String... args) {
        super(code.format(args));
        this.code = code;
        if (args != null) {
            this.args.addAll(Arrays.asList(args));
        }
    }
}
