package com.campus.lostfound.common;

public record ApiResponse<T>(
        int code,
        String message,
        T data
) {

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }
}