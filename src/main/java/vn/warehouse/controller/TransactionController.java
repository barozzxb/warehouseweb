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
import vn.warehouse.dto.request.TransactionRequest;
import vn.warehouse.dto.response.PageResponse;
import vn.warehouse.dto.response.TransactionResponse;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.model.enumuration.TransactionType;
import vn.warehouse.service.TransactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
@Slf4j(topic = "TRANSACTION_CONTROLLER")
@Tag(name = "Giao dịch", description = "API Quản lý Giao dịch")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(summary = "Tạo giao dịch mới", description = "Tạo một giao dịch mới với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Giao dịch được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm, nhân viên hoặc nhà cung cấp")
    })
    @PostMapping
    public ResponseData<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest request) {
        log.info("Create transaction request: {}", request);
        return ResponseData.<TransactionResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo giao dịch thành công")
                .data(transactionService.createTransaction(request))
                .build();
    }

    @Operation(summary = "Lấy giao dịch theo ID", description = "Lấy thông tin giao dịch theo ID, bao gồm sản phẩm, nhân viên và nhà cung cấp liên quan.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy giao dịch thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy giao dịch")
    })
    @GetMapping("/{id}")
    public ResponseData<TransactionResponse> getTransactionById(@PathVariable Long id) {
        log.info("Get transaction request for id: {}", id);
        return ResponseData.<TransactionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy giao dịch thành công")
                .data(transactionService.getTransactionById(id))
                .build();
    }

    @Operation(summary = "Lấy tất cả giao dịch", description = "Lấy danh sách giao dịch, hỗ trợ phân trang và lọc theo type, status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách giao dịch thành công"),
            @ApiResponse(responseCode = "400", description = "Tham số không hợp lệ")
    })
    @GetMapping
    public ResponseData<PageResponse<TransactionResponse>> getAllTransactions(
            @Parameter(description = "Số trang (bắt đầu từ 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Kích thước trang", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Trường để sắp xếp", example = "transactionDate")
            @RequestParam(defaultValue = "transactionDate") String sort,
            @Parameter(description = "Hướng sắp xếp (asc hoặc desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Loại giao dịch (IMPORT/EXPORT)", example = "IMPORT")
            @RequestParam(required = false) TransactionType type,
            @Parameter(description = "Trạng thái giao dịch", example = "COMPLETED")
            @RequestParam(required = false) String status) {
        log.info("Get all transactions request with page: {}, size: {}, type: {}, status: {}", page, size, type, status);
        return ResponseData.<PageResponse<TransactionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy danh sách giao dịch thành công")
                .data(transactionService.getAllTransactions(page, size, sort, direction, type, status))
                .build();
    }

    @Operation(summary = "Cập nhật giao dịch", description = "Cập nhật giao dịch hiện có theo ID với thông tin được cung cấp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật giao dịch thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy giao dịch, sản phẩm, nhân viên hoặc nhà cung cấp"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ")
    })
    @PutMapping("/{id}")
    public ResponseData<TransactionResponse> updateTransaction(@PathVariable Long id, @RequestBody @Valid TransactionRequest request) {
        log.info("Update transaction request for id: {}", id);
        return ResponseData.<TransactionResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Cập nhật giao dịch thành công")
                .data(transactionService.updateTransaction(id, request))
                .build();
    }

    @Operation(summary = "Xóa giao dịch", description = "Xóa giao dịch theo ID, kiểm tra ảnh hưởng đến tồn kho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Xóa giao dịch thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy giao dịch"),
            @ApiResponse(responseCode = "400", description = "Không thể xóa giao dịch do ảnh hưởng đến tồn kho")
    })
    @DeleteMapping("/{id}")
    public ResponseData<Void> deleteTransaction(@PathVariable Long id) {
        log.info("Delete transaction request for id: {}", id);
        transactionService.deleteTransaction(id);
        return ResponseData.<Void>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Xóa giao dịch thành công")
                .data(null)
                .build();
    }

    @Operation(summary = "Tạo giao dịch nhập hàng", description = "Tạo giao dịch nhập hàng, tăng số lượng tồn kho của sản phẩm.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Giao dịch nhập hàng được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm, nhân viên hoặc nhà cung cấp")
    })
    @PostMapping("/import")
    public ResponseData<TransactionResponse> createImportTransaction(@RequestBody @Valid TransactionRequest request) {
        log.info("Create import transaction request: {}", request);
        return ResponseData.<TransactionResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo giao dịch nhập hàng thành công")
                .data(transactionService.createImportTransaction(request))
                .build();
    }

    @Operation(summary = "Tạo giao dịch xuất hàng", description = "Tạo giao dịch xuất hàng, giảm số lượng tồn kho của sản phẩm.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Giao dịch xuất hàng được tạo thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu yêu cầu không hợp lệ hoặc không đủ hàng tồn kho"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm, nhân viên hoặc nhà cung cấp")
    })
    @PostMapping("/export")
    public ResponseData<TransactionResponse> createExportTransaction(@RequestBody @Valid TransactionRequest request) {
        log.info("Create export transaction request: {}", request);
        return ResponseData.<TransactionResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Tạo giao dịch xuất hàng thành công")
                .data(transactionService.createExportTransaction(request))
                .build();
    }
}