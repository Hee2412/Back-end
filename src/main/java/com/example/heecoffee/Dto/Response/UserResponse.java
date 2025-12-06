package com.example.heecoffee.Dto.Response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {
    private Integer id;
    private String email;
    private String name;
    private String address;
    private Integer age;
    private String role;

    public UserResponse(Integer id, String email, String name, String address, Integer age, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.age = age;
        this.role = role;
    }
}
