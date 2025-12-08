package com.example.heecoffee.Service.impl;

import com.example.heecoffee.Dto.Request.CreateUserRequest;
import com.example.heecoffee.Dto.Request.UpdateUserByAdminRequest;
import com.example.heecoffee.Dto.Request.UpdateUserRequest;
import com.example.heecoffee.Dto.Response.UserResponse;
import com.example.heecoffee.Exception.ErrorCodeConstant;
import com.example.heecoffee.Exception.NotFoundException;
import com.example.heecoffee.Model.User;
import com.example.heecoffee.Repository.UserRepository;
import com.example.heecoffee.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Invalid email or password", ErrorCodeConstant.INVALID_USERNAME_OR_PASSWORD));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return user;
    }

    @Override
    public User create(CreateUserRequest dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email already exists");
        }
        String encodePassword = passwordEncoder.encode(dto.getPassword());
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(encodePassword);
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setAddress(dto.getAddress());
        user.setRole("USER");
        return userRepository.save(user);
    }

    @Override
    public User update(UpdateUserRequest dto, String emailFromToken) {
        User user = userRepository.findByEmail(emailFromToken)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCodeConstant.USER_NOT_FOUND));

        user.setName(dto.getName());
        user.setAddress(dto.getAddress());

        return userRepository.save(user);
    }

    @Override
    public long countUser() {
        return userRepository.count();
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> user = userRepository.findAll();
        return user.stream().map(u -> new UserResponse(
                u.getId(),
                u.getEmail(),
                u.getName(),
                u.getAddress(),
                u.getAge(),
                u.getRole()
        )).toList();
    }

    @Override
    public User updateUserByAdmin(UpdateUserByAdminRequest dto, String oldEmailUser) {
        User user = userRepository.findByEmail(oldEmailUser)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCodeConstant.USER_NOT_FOUND));

        user.setName(dto.getName());
        user.setEmail(dto.getNewEmail());
        user.setAddress(dto.getAddress());
        user.setAge(dto.getAge());
        user.setRole(dto.getRole());
        return userRepository.save(user);
    }

    @Override
    public void deleteUserByAdmin(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found", ErrorCodeConstant.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
