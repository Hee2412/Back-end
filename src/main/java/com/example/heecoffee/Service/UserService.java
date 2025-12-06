package com.example.heecoffee.Service;

import com.example.heecoffee.Dto.Request.CreateUserRequest;
import com.example.heecoffee.Dto.Request.UpdateUserRequest;
import com.example.heecoffee.Model.User;

public interface UserService {
    User login(String email, String password);

    User create(CreateUserRequest dto);

    User update(UpdateUserRequest dto, String emailFromToken);


    long countUser();
}
