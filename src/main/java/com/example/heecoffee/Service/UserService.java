package com.example.heecoffee.Service;

import com.example.heecoffee.Dto.Request.CreateUserRequest;
import com.example.heecoffee.Dto.Request.UpdateUserByAdminRequest;
import com.example.heecoffee.Dto.Request.UpdateUserRequest;
import com.example.heecoffee.Dto.Response.UserResponse;
import com.example.heecoffee.Model.User;

import java.util.List;

public interface UserService {
    User login(String email, String password);

    User create(CreateUserRequest dto);

    User update(UpdateUserRequest dto, String emailFromToken);


    long countUser();

    List<UserResponse> findAllUsers();

    User updateUserByAdmin(UpdateUserByAdminRequest dto, String targetUserEmail);

    void deleteUserByAdmin(Integer userId);
}
