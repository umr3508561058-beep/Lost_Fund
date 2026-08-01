package com.campus.lostfound.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "学号不能为空")
        @Size(max = 30, message = "学号长度不能超过30位")
        String studentNo,

        @NotBlank(message = "用户名不能为空")
        @Size(max = 50, message = "用户名长度不能超过50位")
        String username,

        @NotBlank(message = "密码不能为空")
        @Size(min = 6, max = 50, message = "密码长度必须为6到50位")
        String password,

        String phone
) {
}