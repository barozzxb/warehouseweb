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
import vn.warehouse.dto.request.CategoryRequest;
import vn.warehouse.dto.response.CategoryResponse;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.ProductResponse;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.service.CategoryService;
import vn.warehouse.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Slf4j(topic = "CATEGORY_CONTROLLER")
@Tag(name = "Danh mục", description = "API Quản lý Danh mục")
public class CategoryController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @Operation(summary = "Tạo danh mục mới", description = "Tạo một danh mục mới với tên và mô tả được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Danh mục được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PostMapping
    public ResponseData<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest request) {
        log.info("Create category request: {}", request);
        return ResponseData.<CategoryResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo danh mục thành công")
                .data(categoryService.createCategory(request))
                .build();
    }

    @Operation(summary = "Lấy danh mục theo ID", description = "Lấy thông tin danh mục theo ID, bao gồm danh sách ID sản phẩm liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh mục thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục")
    })
    @GetMapping("/{id}")
    public ResponseData<CategoryResponse> getCategoryById(@PathVariable Long id) {
        log.info("Get category request for id: {}", id);
        return ResponseData.<CategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh mục thành công")
                .data(categoryService.getCategoryById(id))
                .build();
    }

    @Operation(summary = "Lấy tất cả danh mục", description = "Lấy danh sách tất cả các danh mục.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách danh mục thành công")
    })

    @GetMapping
    public ResponseData<PageResponse<CategoryResponse>> getAllCategories(
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "name")
            @RequestParam(defaultValue = "name") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Từ khóa tìm kiếm theo tên danh mục", example = "Electronics")
            @RequestParam(required = false) String name) {
        log.info("Get all categories request with page: {}, size: {}, name: {}", page, size, name);
        return ResponseData.<PageResponse<CategoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách danh mục thành công")
                .data(categoryService.getAllCategories(page, size, sort, direction, name))
                .build();
    }

    @Operation(summary = "Cập nhật danh mục", description = "Cập nhật danh mục hiện có theo ID với tên và mô tả được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật danh mục thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PutMapping("/{id}")
    public ResponseData<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequest request) {
        log.info("Update category request for id: {}", id);
        return ResponseData.<CategoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật danh mục thành công")
                .data(categoryService.updateCategory(id, request))
                .build();
    }

    @Operation(summary = "Xóa danh mục", description = "Xóa danh mục theo ID, chỉ khi không có sản phẩm liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa danh mục thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục"),
            @ApiResponse(responseCode = "400", description = "Danh mục còn sản phẩm liên quan, không thể xóa")
    })
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteCategory(@PathVariable Long id) {
        log.info("Delete category request for id: {}", id);
        categoryService.deleteCategory(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa danh mục thành công")
                .data(null)
                .build();
    }

    @GetMapping("/{id}/products")
    public ResponseData<PageResponse<ProductResponse>> getProductsByCategory(
            @PathVariable Long id,
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "name")
            @RequestParam(defaultValue = "name") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get products for category id: {}", id);
        return ResponseData.<PageResponse<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm theo danh mục thành công")
                .data(productService.getProductsByCategory(id, page, size, sort, direction))
                .build();
    }
}