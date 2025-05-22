package vn.warehouse.service;

import vn.warehouse.dto.request.UserRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.UserResponse;
import vn.warehouse.model.enumuration.Role;

public interface UserService {
    PageResponse<UserResponse> getAllUsers(int page, int size, String sort, String direction, String search, Role role);    UserResponse getUserById(Long id);
    UserResponse createUser(UserRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
}
