package com.campus.lostfound.dto;

public record LoginResponse(
        String token,
        String tokenType,
        Long id,
        String studentNo,
        String username,
        String role
) {
}