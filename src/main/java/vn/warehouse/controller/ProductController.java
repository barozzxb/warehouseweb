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
import vn.warehouse.dto.request.ProductRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.ProductResponse;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.service.ProductService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Slf4j(topic = "PRODUCT_CONTROLLER")
@Tag(name = "Sản phẩm", description = "API Quản lý Sản phẩm")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Tạo sản phẩm mới", description = "Tạo một sản phẩm mới với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sản phẩm được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục hoặc kho")
    })
    @PostMapping
    public ResponseData<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        log.info("Create product request: {}", request);
        return ResponseData.<ProductResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo sản phẩm thành công")
                .data(productService.createProduct(request))
                .build();
    }

    @Operation(summary = "Lấy sản phẩm theo ID", description = "Lấy thông tin sản phẩm theo ID, bao gồm danh mục, kho, nhà cung cấp và giao dịch liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy sản phẩm thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @GetMapping("/{id}")
    public ResponseData<ProductResponse> getProductById(@PathVariable Long id) {
        log.info("Get product request for id: {}", id);
        return ResponseData.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy sản phẩm thành công")
                .data(productService.getProductById(id))
                .build();
    }

    @Operation(summary = "Lấy tất cả sản phẩm (phân trang)", 
               description = "Lấy danh sách sản phẩm theo trang, với các tham số phân trang và sắp xếp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách sản phẩm thành công"),
            @ApiResponse(responseCode = "400", description = "Tham số phân trang hoặc sắp xếp không hợp lệ")
    })
    @GetMapping
    public ResponseData<PageResponse<ProductResponse>> getAllProducts(
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1") 
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10") 
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "name") 
            @RequestParam(defaultValue = "name") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc") 
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get all products request with page: {}, size: {}, sort: {}, direction: {}", page, size, sort, direction);
        return ResponseData.<PageResponse<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm thành công")
                .data(productService.getAllProducts(page, size, sort, direction))
                .build();
    }

    @Operation(summary = "Cập nhật sản phẩm", description = "Cập nhật sản phẩm hiện có theo ID với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật sản phẩm thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm, danh mục hoặc kho"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PutMapping("/{id}")
    public ResponseData<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest request) {
        log.info("Update product request for id: {}", id);
        return ResponseData.<ProductResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật sản phẩm thành công")
                .data(productService.updateProduct(id, request))
                .build();
    }

    @Operation(summary = "Xóa sản phẩm", description = "Xóa sản phẩm theo ID, chỉ khi không có giao dịch liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa sản phẩm thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm"),
            @ApiResponse(responseCode = "400", description = "Sản phẩm còn giao dịch liên quan, không thể xóa")
    })
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteProduct(@PathVariable Long id) {
        log.info("Delete product request for id: {}", id);
        productService.deleteProduct(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa sản phẩm thành công")
                .data(null)
                .build();
    }

    @Operation(summary = "Lấy danh sách sản phẩm sắp hết hạn", description = "Lấy danh sách sản phẩm sắp hết hạn dựa trên expiryDate, hỗ trợ phân trang.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách sản phẩm sắp hết hạn thành công"),
            @ApiResponse(responseCode = "400", description = "Tham số không hợp lệ")
    })

    @GetMapping("/expiring")
    public ResponseData<PageResponse<ProductResponse>> getExpiringProducts(
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "expiryDate")
            @RequestParam(defaultValue = "expiryDate") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Số ngày kể từ hiện tại để xác định sản phẩm sắp hết hạn", example = "30")
            @RequestParam(defaultValue = "30") int days) {
        log.info("Get expiring products request with days: {}", days);
        return ResponseData.<PageResponse<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm sắp hết hạn thành công")
                .data(productService.getExpiringProducts(page, size, sort, direction, days))
                .build();
    }
}