package com.campus.lostfound.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "学号不能为空")
        String studentNo,

        @NotBlank(message = "密码不能为空")
        String password
) {
}