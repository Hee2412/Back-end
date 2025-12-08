package com.example.heecoffee.Controller;

import com.example.heecoffee.Config.CustomerUserDetails;
import com.example.heecoffee.Config.JwtTokenProvider;
import com.example.heecoffee.Dto.Request.CreateUserRequest;
import com.example.heecoffee.Dto.Request.LoginRequest;
import com.example.heecoffee.Dto.Request.UpdateUserByAdminRequest;
import com.example.heecoffee.Dto.Request.UpdateUserRequest;
import com.example.heecoffee.Dto.Response.ApiResponse;
import com.example.heecoffee.Dto.Response.JwtResponse;
import com.example.heecoffee.Dto.Response.UserResponse;
import com.example.heecoffee.Model.User;
import com.example.heecoffee.Service.OrderService;
import com.example.heecoffee.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final OrderService orderService;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, OrderService orderService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.orderService = orderService;
    }

    @GetMapping("/count")
    public ResponseEntity<?> getUserCount() {
        long count = userService.countUser();
        return ResponseEntity.ok(Map.of("count", count));
    }

    //1. Login api
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        User user = userService.login(login.getEmail(), login.getPassword()); // check password
        String token = jwtTokenProvider.generateToken(user.getEmail());

        UserResponse dto = new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getAddress(),
                user.getAge(),
                user.getRole()
        );
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token, "Login success", dto));
    }

    //2. Register api
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody CreateUserRequest dto) {
        User user = userService.create(dto);
        orderService.syncGuestOrderToUser(user.getEmail(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Register success", user));
    }

    //3. Update api
    @PutMapping("/me")
    public ResponseEntity<ApiResponse> update(@Valid @RequestBody UpdateUserRequest dto,
                                              @AuthenticationPrincipal CustomerUserDetails userDetails) {
        User user = userService.update(dto, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Update success", user));
    }

    //ADMIN
    //1. Get all user
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllUsers(@AuthenticationPrincipal CustomerUserDetails userDetails) {
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("ACCESS DENIED!", null));
        }
        List<UserResponse> user = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Get all users", user));
    }

    //2. Update user by Admin
    @PutMapping()
    public ResponseEntity<ApiResponse> updateUserByAdmin(
            @RequestBody UpdateUserByAdminRequest dto,
            @AuthenticationPrincipal CustomerUserDetails userDetails
    ) {
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("ACCESS DENIED!", null));
        }
        User user = userService.updateUserByAdmin(dto, dto.getOldEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Get all users", user));
    }

    //3. Delete user by Admin
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUserByAdmin(
            @AuthenticationPrincipal CustomerUserDetails userDetails,
            @PathVariable Integer userId
    ){
        if(!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse("ACCESS DENIED!", null));
        }
        userService.deleteUserByAdmin(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Success", null));
    }

}
