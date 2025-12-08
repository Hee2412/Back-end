package com.example.heecoffee.Dto.Request;

import lombok.Data;
import javax.validation.constraints.*;

@Data
public class UpdateUserByAdminRequest {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String oldEmail;

    @Email
    @NotBlank
    private String newEmail;

    @NotNull
    @Min(14)
    @Max(80)
    private Integer age;

    private String address;

    @NotBlank
    private String role;
}
