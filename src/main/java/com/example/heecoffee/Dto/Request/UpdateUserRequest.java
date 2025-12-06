package com.example.heecoffee.Dto.Request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
