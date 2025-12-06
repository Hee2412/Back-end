package com.example.heecoffee.Dto.Response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtResponse {
    private String token;
    private String message;
    private UserResponse user;

    public JwtResponse(String token, String message, UserResponse user) {
        this.token = token;
        this.message = message;
        this.user = user;
    }
}
