package com.demo.springbootsecurityjwtdemo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorResponseDto implements Serializable {
    private String code;
    private String description;
    private List<String> args;
}
