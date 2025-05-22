package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.warehouse.dto.request.LoginRequest;
import vn.warehouse.dto.request.RegisterRequest;
import vn.warehouse.dto.response.TokenResponse;
import vn.warehouse.dto.response.UserResponse;
import vn.warehouse.model.User;
import vn.warehouse.model.enumuration.Role;
import vn.warehouse.repository.UserRepository;
import vn.warehouse.service.AuthService;
import vn.warehouse.service.JwtService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse login(LoginRequest request){
        log.info("Login with username: {}", request.getUsername());

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            log.debug("Xác thực thành công: {}", authenticate.isAuthenticated());
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (BadCredentialsException | DisabledException e) {
            log.error("Lỗi xác thực: {}", e.getMessage());
            throw new AccessDeniedException("Thông tin đăng nhập không hợp lệ hoặc tài khoản bị khóa");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AccessDeniedException("Tài khoản không tồn tại"));

        String token = jwtService.generateToken(user);

        return TokenResponse.builder()
                .token(token)
                .userId(user.getId())
                .build();
    }

    @Override
    public UserResponse register(RegisterRequest request){
        log.info("Register with username: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadCredentialsException("Tên đăng nhập đã tồn tại");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(Role.STAFF)
                .build();

        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
