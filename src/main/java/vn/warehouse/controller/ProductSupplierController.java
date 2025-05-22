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
import vn.warehouse.dto.request.ProductSupplierRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.ProductSupplierResponse;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.service.ProductSupplierService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product-suppliers")
@Slf4j(topic = "PRODUCT_SUPPLIER_CONTROLLER")
@Tag(name = "Sản phẩm-Nhà cung cấp", description = "API Quản lý Quan hệ Sản phẩm-Nhà cung cấp")
public class ProductSupplierController {
    private final ProductSupplierService productSupplierService;

    @Operation(summary = "Tạo quan hệ sản phẩm-nhà cung cấp mới", description = "Tạo một quan hệ sản phẩm-nhà cung cấp mới với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Quan hệ sản phẩm-nhà cung cấp được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm hoặc nhà cung cấp")
    })
    @PostMapping
    public ResponseData<ProductSupplierResponse> createProductSupplier(@RequestBody @Valid ProductSupplierRequest request) {
        log.info("Create product-supplier request: {}", request);
        return ResponseData.<ProductSupplierResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo quan hệ sản phẩm-nhà cung cấp thành công")
                .data(productSupplierService.createProductSupplier(request))
                .build();
    }

    @Operation(summary = "Lấy quan hệ sản phẩm-nhà cung cấp theo ID", description = "Lấy thông tin quan hệ sản phẩm-nhà cung cấp theo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy quan hệ sản phẩm-nhà cung cấp thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy quan hệ sản phẩm-nhà cung cấp")
    })
    @GetMapping("/{id}")
    public ResponseData<ProductSupplierResponse> getProductSupplierById(@PathVariable Long id) {
        log.info("Get product-supplier request for id: {}", id);
        return ResponseData.<ProductSupplierResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy quan hệ sản phẩm-nhà cung cấp thành công")
                .data(productSupplierService.getProductSupplierById(id))
                .build();
    }

    @Operation(summary = "Lấy tất cả quan hệ sản phẩm-nhà cung cấp (phân trang)", 
               description = "Lấy danh sách quan hệ sản phẩm-nhà cung cấp theo trang, với các tham số phân trang và sắp xếp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách quan hệ sản phẩm-nhà cung cấp thành công"),
            @ApiResponse(responseCode = "400", description = "Tham số phân trang hoặc sắp xếp không hợp lệ")
    })
    @GetMapping
    public ResponseData<PageResponse<ProductSupplierResponse>> getAllProductSuppliers(
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1") 
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10") 
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "supplyDate") 
            @RequestParam(defaultValue = "supplyDate") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc") 
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get all product-suppliers request with page: {}, size: {}, sort: {}, direction: {}", page, size, sort, direction);
        return ResponseData.<PageResponse<ProductSupplierResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách quan hệ sản phẩm-nhà cung cấp thành công")
                .data(productSupplierService.getAllProductSuppliers(page, size, sort, direction))
                .build();
    }

    @Operation(summary = "Cập nhật quan hệ sản phẩm-nhà cung cấp", description = "Cập nhật quan hệ sản phẩm-nhà cung cấp hiện có theo ID với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật quan hệ sản phẩm-nhà cung cấp thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy quan hệ sản phẩm-nhà cung cấp, sản phẩm hoặc nhà cung cấp"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PutMapping("/{id}")
    public ResponseData<ProductSupplierResponse> updateProductSupplier(@PathVariable Long id, @RequestBody @Valid ProductSupplierRequest request) {
        log.info("Update product-supplier request for id: {}", id);
        return ResponseData.<ProductSupplierResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật quan hệ sản phẩm-nhà cung cấp thành công")
                .data(productSupplierService.updateProductSupplier(id, request))
                .build();
    }

    @Operation(summary = "Xóa quan hệ sản phẩm-nhà cung cấp", description = "Xóa quan hệ sản phẩm-nhà cung cấp theo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa quan hệ sản phẩm-nhà cung cấp thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy quan hệ sản phẩm-nhà cung cấp")
    })
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteProductSupplier(@PathVariable Long id) {
        log.info("Delete product-supplier request for id: {}", id);
        productSupplierService.deleteProductSupplier(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa quan hệ sản phẩm-nhà cung cấp thành công")
                .data(null)
                .build();
    }
}