package com.example.heecoffee.Dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String message;
    private Object data;
    private String errorCode;

    public ApiResponse(String message, Object data, String errorCode) {
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
    }

    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
        this.errorCode = null;
    }
}
