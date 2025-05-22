package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.warehouse.dto.request.UserRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.UserResponse;
import vn.warehouse.exception.ConflictException;
import vn.warehouse.exception.ResourceNotFoundException;
import vn.warehouse.model.User;
import vn.warehouse.model.enumuration.Role;
import vn.warehouse.repository.UserRepository;
import vn.warehouse.service.UserService;
import vn.warehouse.util.PaginationUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size, String sort, String direction, String search, Role role) {
        log.info("Fetching all users with page: {}, size: {}, sort: {}, direction: {}, search: {}, role: {}",
                page, size, sort, direction, search, role);

        Pageable pageable = PaginationUtil.createPageable(page, size, sort, direction);
        Page<User> userPage;

        if (search != null && !search.trim().isEmpty() && role != null) {
            userPage = userRepository.findByUsernameContainingIgnoreCaseOrPhoneContainingAndRole(
                    search, search, role, pageable);
        } else if (search != null && !search.trim().isEmpty()) {
            userPage = userRepository.findByUsernameContainingIgnoreCaseOrPhoneContaining(search, search, pageable);
        } else if (role != null) {
            userPage = userRepository.findByRole(role, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        return PageResponse.<UserResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .content(userPage.getContent().stream()
                        .map(this::mapToResponse)
                        .toList())
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.info("Fetching user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id));
        return mapToResponse(user);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {
        log.info("Creating user with username: {}", request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username đã tồn tại: " + request.getUsername());
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole())
                .build();
        user = userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        log.info("Updating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id));

        if (!user.getUsername().equals(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Username đã tồn tại: " + request.getUsername());
        }

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user = userRepository.save(user);
        return mapToResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với id: " + id));

        if (!user.getTransactions().isEmpty()) {
            throw new ConflictException("Không thể xóa người dùng vì có giao dịch liên quan");
        }

        userRepository.deleteById(id);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        return response;
    }
}
