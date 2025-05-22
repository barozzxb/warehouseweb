package vn.warehouse.service;

import vn.warehouse.dto.request.LoginRequest;
import vn.warehouse.dto.request.RegisterRequest;
import vn.warehouse.dto.response.TokenResponse;
import vn.warehouse.dto.response.UserResponse;

public interface AuthService {
    TokenResponse login(LoginRequest request);

    UserResponse register(RegisterRequest request);
}
