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
import vn.warehouse.dto.request.InventoryRequest;
import vn.warehouse.dto.response.InventoryResponse;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.ProductResponse;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.service.InventoryService;
import vn.warehouse.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventories")
@Slf4j(topic = "INVENTORY_CONTROLLER")
@Tag(name = "Kho", description = "API Quản lý Kho")
public class InventoryController {
    private final InventoryService inventoryService;
    private final ProductService productService;

    @Operation(summary = "Tạo kho mới", description = "Tạo một kho mới với ID kho hàng được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kho được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho hàng")
    })
    @PostMapping
    public ResponseData<InventoryResponse> createInventory(@RequestBody @Valid InventoryRequest request) {
        log.info("Create inventory request for warehouse id: {}", request.getWarehouseId());
        return ResponseData.<InventoryResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo kho thành công")
                .data(inventoryService.createInventory(request))
                .build();
    }

    @Operation(summary = "Lấy kho theo ID", description = "Lấy thông tin kho theo ID, bao gồm ID kho hàng và danh sách ID sản phẩm liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy kho thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho")
    })
    @GetMapping("/{id}")
    public ResponseData<InventoryResponse> getInventoryById(@PathVariable Long id) {
        log.info("Get inventory request for id: {}", id);
        return ResponseData.<InventoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy kho thành công")
                .data(inventoryService.getInventoryById(id))
                .build();
    }

    @Operation(summary = "Lấy tất cả kho", description = "Lấy danh sách tất cả các kho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách kho thành công")
    })
    @GetMapping
    public ResponseData<List<InventoryResponse>> getAllInventories() {
        log.info("Get all inventories request");
        return ResponseData.<List<InventoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách kho thành công")
                .data(inventoryService.getAllInventories())
                .build();
    }

    @Operation(summary = "Cập nhật kho", description = "Cập nhật kho hiện có theo ID với ID kho hàng được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật kho thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho hoặc kho hàng"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PutMapping("/{id}")
    public ResponseData<InventoryResponse> updateInventory(@PathVariable Long id, @RequestBody @Valid InventoryRequest request) {
        log.info("Update inventory request for id: {}", id);
        return ResponseData.<InventoryResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật kho thành công")
                .data(inventoryService.updateInventory(id, request))
                .build();
    }

    @Operation(summary = "Xóa kho", description = "Xóa kho theo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa kho thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho")
    })
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteInventory(@PathVariable Long id) {
        log.info("Delete inventory request for id: {}", id);
        inventoryService.deleteInventory(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa kho thành công")
                .data(null)
                .build();
    }

    @Operation(summary = "Lấy danh sách sản phẩm trong kho", description = "Lấy danh sách sản phẩm thuộc kho con theo ID, hỗ trợ phân trang.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách sản phẩm thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho")
    })
    @GetMapping("/{id}/products")
    public ResponseData<PageResponse<ProductResponse>> getProductsByInventory(
            @PathVariable Long id,
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "name")
            @RequestParam(defaultValue = "name") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get products for inventory id: {}", id);
        return ResponseData.<PageResponse<ProductResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm trong kho thành công")
                .data(productService.getProductsByInventory(id, page, size, sort, direction))
                .build();
    }
}