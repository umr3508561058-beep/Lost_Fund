package com.campus.lostfound.controller;

import com.campus.lostfound.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me(
            HttpServletRequest request
    ) {
        Map<String, Object> userInfo = new LinkedHashMap<>();

        userInfo.put("id", request.getAttribute("userId"));
        userInfo.put("studentNo", request.getAttribute("studentNo"));
        userInfo.put("username", request.getAttribute("username"));
        userInfo.put("role", request.getAttribute("role"));

        return ApiResponse.success("获取当前用户成功", userInfo);
    }
}