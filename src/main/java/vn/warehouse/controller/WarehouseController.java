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
import vn.warehouse.dto.request.WarehouseRequest;
import vn.warehouse.dto.response.InventoryResponse;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.WarehouseResponse;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.service.InventoryService;
import vn.warehouse.service.WarehouseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouses")
@Slf4j(topic = "WAREHOUSE_CONTROLLER")
@Tag(name = "Kho hàng", description = "API Quản lý Kho hàng")
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final InventoryService inventoryService;

    @Operation(summary = "Tạo kho hàng mới", description = "Tạo một kho hàng mới với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kho hàng được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PostMapping
    public ResponseData<WarehouseResponse> createWarehouse(@RequestBody @Valid WarehouseRequest request) {
        log.info("Create warehouse request: {}", request);
        return ResponseData.<WarehouseResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo kho hàng thành công")
                .data(warehouseService.createWarehouse(request))
                .build();
    }

    @Operation(summary = "Lấy kho hàng theo ID", description = "Lấy thông tin kho hàng theo ID, bao gồm danh sách nhà cung cấp và kho liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy kho hàng thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho hàng")
    })
    @GetMapping("/{id}")
    public ResponseData<WarehouseResponse> getWarehouseById(@PathVariable Long id) {
        log.info("Get warehouse request for id: {}", id);
        return ResponseData.<WarehouseResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy kho hàng thành công")
                .data(warehouseService.getWarehouseById(id))
                .build();
    }

    @Operation(summary = "Lấy tất cả kho hàng (phân trang)", 
               description = "Lấy danh sách kho hàng theo trang, với các tham số phân trang và sắp xếp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách kho hàng thành công"),
            @ApiResponse(responseCode = "400", description = "Tham số phân trang hoặc sắp xếp không hợp lệ")
    })
    @GetMapping
    public ResponseData<PageResponse<WarehouseResponse>> getAllWarehouses(
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1") 
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10") 
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "name") 
            @RequestParam(defaultValue = "name") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc") 
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get all warehouses request with page: {}, size: {}, sort: {}, direction: {}", page, size, sort, direction);
        return ResponseData.<PageResponse<WarehouseResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách kho hàng thành công")
                .data(warehouseService.getAllWarehouses(page, size, sort, direction))
                .build();
    }

    @Operation(summary = "Cập nhật kho hàng", description = "Cập nhật kho hàng hiện có theo ID với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật kho hàng thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho hàng"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PutMapping("/{id}")
    public ResponseData<WarehouseResponse> updateWarehouse(@PathVariable Long id, @RequestBody @Valid WarehouseRequest request) {
        log.info("Update warehouse request for id: {}", id);
        return ResponseData.<WarehouseResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật kho hàng thành công")
                .data(warehouseService.updateWarehouse(id, request))
                .build();
    }

    @Operation(summary = "Xóa kho hàng", description = "Xóa kho hàng theo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa kho hàng thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho hàng")
    })
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteWarehouse(@PathVariable Long id) {
        log.info("Delete warehouse request for id: {}", id);
        warehouseService.deleteWarehouse(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa kho hàng thành công")
                .data(null)
                .build();
    }

    @Operation(summary = "Lấy danh sách kho con trong kho tổng", description = "Lấy danh sách kho con thuộc kho tổng theo ID, hỗ trợ phân trang.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách kho con thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy kho tổng")
    })
    @GetMapping("/{id}/inventories")
    public ResponseData<PageResponse<InventoryResponse>> getInventoriesByWarehouse(
            @PathVariable Long id,
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "id")
            @RequestParam(defaultValue = "id") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {
        log.info("Get inventories for warehouse id: {}", id);
        return ResponseData.<PageResponse<InventoryResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách kho con thành công")
                .data(inventoryService.getInventoriesByWarehouse(id, page, size, sort, direction))
                .build();
    }
}