package com.example.heecoffee.Dto.Request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


@Getter
@Setter
public class CreateUserRequest {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    @Max(80)
    @Min(14)
    private Integer age;

    @NotBlank
    private String address;
}
