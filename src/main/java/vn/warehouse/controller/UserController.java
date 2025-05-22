package vn.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.warehouse.dto.request.UserRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.TransactionResponse;
import vn.warehouse.dto.response.UserResponse;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.model.enumuration.Role;
import vn.warehouse.service.TransactionService;
import vn.warehouse.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j(topic = "USER_CONTROLLER")
@Tag(name = "Người dùng", description = "API Quản lý Người dùng")
public class UserController {
    private final UserService userService;
    private final TransactionService transactionService;

    @Operation(summary = "Lấy danh sách người dùng", description = "Lấy danh sách người dùng, hỗ trợ tìm kiếm theo tên hoặc số điện thoại, lọc theo vai trò, và phân trang.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách người dùng thành công"),
            @ApiResponse(responseCode = "400", description = "Tham số phân trang hoặc vai trò không hợp lệ")
    })
    @GetMapping
    public ResponseData<PageResponse<UserResponse>> getAllUsers(
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "username")
            @RequestParam(defaultValue = "username") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Từ khóa tìm kiếm (tên hoặc số điện thoại)", example = "")
            @RequestParam(required = false) String search,
            @Parameter(description = "Vai trò để lọc (ADMIN hoặc USER)", example = "ADMIN")
            @RequestParam(required = false) Role role) {
        log.info("Get all users request with page: {}, size: {}, sort: {}, direction: {}, search: {}, role: {}",
                page, size, sort, direction, search, role);
        return ResponseData.<PageResponse<UserResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách người dùng thành công")
                .data(userService.getAllUsers(page, size, sort, direction, search, role))
                .build();
    }

    @Operation(summary = "Lấy thông tin người dùng theo ID", description = "Lấy chi tiết thông tin người dùng theo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin người dùng thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng")
    })
    @GetMapping("/{id}")
    public ResponseData<UserResponse> getUserById(@PathVariable Long id) {
        log.info("Get user request for id: {}", id);
        return ResponseData.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy thông tin người dùng thành công")
                .data(userService.getUserById(id))
                .build();
    }

    @Operation(summary = "Tạo người dùng mới", description = "Tạo người dùng mới với thông tin username, password, email, phone, role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Người dùng được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ hoặc username/email đã tồn tại")
    })
    @PostMapping
    public ResponseData<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
        log.info("Create user request: {}", request.getUsername());
        return ResponseData.<UserResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo người dùng thành công")
                .data(userService.createUser(request))
                .build();
    }

    @Operation(summary = "Cập nhật người dùng", description = "Cập nhật thông tin người dùng theo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật người dùng thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PutMapping("/{id}")
    public ResponseData<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest request) {
        log.info("Update user request for id: {}", id);
        return ResponseData.<UserResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật người dùng thành công")
                .data(userService.updateUser(id, request))
                .build();
    }

    @Operation(summary = "Xóa người dùng", description = "Xóa người dùng theo ID, kiểm tra không có giao dịch liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa người dùng thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
            @ApiResponse(responseCode = "400", description = "Không thể xóa do người dùng có giao dịch liên quan")
    })
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteUser(@PathVariable Long id) {
        log.info("Delete user request for id: {}", id);
        userService.deleteUser(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa người dùng thành công")
                .data(null)
                .build();
    }

    @Operation(summary = "Lấy danh sách giao dịch của người dùng", description = "Lấy danh sách giao dịch do người dùng thực hiện, hỗ trợ phân trang.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách giao dịch thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy người dùng"),
            @ApiResponse(responseCode = "400", description = "Tham số phân trang không hợp lệ")
    })
    @GetMapping("/{id}/transactions")
    public ResponseData<PageResponse<TransactionResponse>> getUserTransactions(
            @PathVariable Long id,
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "transactionDate")
            @RequestParam(defaultValue = "transactionDate") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get transactions for user id: {}", id);
        return ResponseData.<PageResponse<TransactionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách giao dịch thành công")
                .data(transactionService.getUserTransactions(id, page, size, sort, direction))
                .build();
    }
}