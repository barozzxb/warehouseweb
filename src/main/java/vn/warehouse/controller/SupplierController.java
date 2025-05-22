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
import vn.warehouse.dto.request.SupplierRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.SupplierResponse;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.service.SupplierService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/suppliers")
@Slf4j(topic = "SUPPLIER_CONTROLLER")
@Tag(name = "Nhà cung cấp", description = "API Quản lý Nhà cung cấp")
public class SupplierController {
    private final SupplierService supplierService;

    @Operation(summary = "Tạo nhà cung cấp mới", description = "Tạo một nhà cung cấp mới với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nhà cung cấp được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho hàng")
    })
    @PostMapping
    public ResponseData<SupplierResponse> createSupplier(@RequestBody @Valid SupplierRequest request) {
        log.info("Create supplier request: {}", request);
        return ResponseData.<SupplierResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo nhà cung cấp thành công")
                .data(supplierService.createSupplier(request))
                .build();
    }

    @Operation(summary = "Lấy nhà cung cấp theo ID", description = "Lấy thông tin nhà cung cấp theo ID, bao gồm kho hàng, quan hệ sản phẩm và giao dịch liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy nhà cung cấp thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy nhà cung cấp")
    })
    @GetMapping("/{id}")
    public ResponseData<SupplierResponse> getSupplierById(@PathVariable Long id) {
        log.info("Get supplier request for id: {}", id);
        return ResponseData.<SupplierResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy nhà cung cấp thành công")
                .data(supplierService.getSupplierById(id))
                .build();
    }

    @Operation(summary = "Lấy tất cả nhà cung cấp (phân trang)", 
               description = "Lấy danh sách nhà cung cấp theo trang, với các tham số phân trang và sắp xếp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách nhà cung cấp thành công"),
            @ApiResponse(responseCode = "400", description = "Tham số phân trang hoặc sắp xếp không hợp lệ")
    })
    @GetMapping
    public ResponseData<PageResponse<SupplierResponse>> getAllSuppliers(
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1") 
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10") 
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "name") 
            @RequestParam(defaultValue = "name") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc") 
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get all suppliers request with page: {}, size: {}, sort: {}, direction: {}", page, size, sort, direction);
        return ResponseData.<PageResponse<SupplierResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách nhà cung cấp thành công")
                .data(supplierService.getAllSuppliers(page, size, sort, direction))
                .build();
    }

    @Operation(summary = "Cập nhật nhà cung cấp", description = "Cập nhật nhà cung cấp hiện có theo ID với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật nhà cung cấp thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy nhà cung cấp hoặc kho hàng"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PutMapping("/{id}")
    public ResponseData<SupplierResponse> updateSupplier(@PathVariable Long id, @RequestBody @Valid SupplierRequest request) {
        log.info("Update supplier request for id: {}", id);
        return ResponseData.<SupplierResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật nhà cung cấp thành công")
                .data(supplierService.updateSupplier(id, request))
                .build();
    }

    @Operation(summary = "Xóa nhà cung cấp", description = "Xóa nhà cung cấp theo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa nhà cung cấp thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy nhà cung cấp")
    })
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteSupplier(@PathVariable Long id) {
        log.info("Delete supplier request for id: {}", id);
        supplierService.deleteSupplier(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa nhà cung cấp thành công")
                .data(null)
                .build();
    }
}