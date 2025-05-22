package vn.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.warehouse.dto.request.LoginRequest;
import vn.warehouse.dto.request.RegisterRequest;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.dto.response.TokenResponse;
import vn.warehouse.dto.response.UserResponse;
import vn.warehouse.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j(topic = "AUTH_CONTROLLER")
@Tag(name = "Auth", description = "Authentication and Registration API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "User Login", description = "Authenticates a user with username and password, returning a JWT token upon success.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, returns JWT token and user ID"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials or account disabled"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/login")
    public ResponseData<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        log.info("Login request: {}", request);
        return ResponseData.<TokenResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Login successful")
                .data(authService.login(request))
                .build();
    }

    @Operation(summary = "User Registration", description = "Registers a new user with username, password, email, and phone, assigning the STAFF role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully, returns user details"),
            @ApiResponse(responseCode = "400", description = "Username already exists"),
            @ApiResponse(responseCode = "500", description = "Server error during registration")
    })
    @PostMapping("/register")
    public ResponseData<UserResponse> register(@RequestBody @Valid RegisterRequest request) {
        log.info("Register request: {}", request);
        return ResponseData.<UserResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Registration successful")
                .data(authService.register(request))
                .build();
    }
}