package vn.warehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.warehouse.dto.response.ResponseData;
import vn.warehouse.dto.response.TransactionStatisticsResponse;
import vn.warehouse.service.StatisticsService;
import vn.warehouse.util.ExportContext;

import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
@Validated
@Tag(name = "Thống kê", description = "API để lấy và xuất thống kê giao dịch kho hàng")
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final ExportContext exportContext;

    @GetMapping("/transactions")
    @Operation(summary = "Lấy thống kê giao dịch", description = "Lấy thống kê giao dịch (số lượng giao dịch và tổng số lượng sản phẩm) được nhóm theo loại và trạng thái trong khoảng thời gian chỉ định.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thống kê giao dịch thành công"),
            @ApiResponse(responseCode = "400", description = "Định dạng ngày không hợp lệ hoặc tham số không hợp lệ"),
            @ApiResponse(responseCode = "500", description = "Lỗi máy chủ nội bộ")
    })
    public ResponseData<List<TransactionStatisticsResponse>> getTransactionStatistics(
            @RequestParam @NotNull(message = "Ngày bắt đầu là bắt buộc")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @Parameter(description = "Ngày và giờ bắt đầu (định dạng ISO 8601, ví dụ: 2024-01-10T09:00:00)", required = true)
            LocalDateTime startDate,

            @RequestParam @NotNull(message = "Ngày kết thúc là bắt buộc")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @Parameter(description = "Ngày và giờ kết thúc (định dạng ISO 8601, ví dụ: 2025-01-01T09:00:00)", required = true)
            LocalDateTime endDate) {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }

        List<TransactionStatisticsResponse> stats = statisticsService.getTransactionStatistics(startDate, endDate);
        return ResponseData.<List<TransactionStatisticsResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Lấy thống kê giao dịch thành công")
                .data(stats)
                .build();
    }

    @GetMapping("/transactions/export")
    @Operation(summary = "Xuất thống kê giao dịch", description = "Xuất thống kê giao dịch ra file PDF, CSV hoặc Excel và lưu vào cơ sở dữ liệu. Tùy chọn lưu vào hệ thống tệp.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xuất file thành công",
                    content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "400", description = "Định dạng không hợp lệ hoặc tham số không hợp lệ"),
            @ApiResponse(responseCode = "500", description = "Lỗi máy chủ nội bộ")
    })
    public ResponseEntity<byte[]> exportTransactionStatistics(
            @RequestParam @NotNull(message = "Ngày bắt đầu là bắt buộc")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @Parameter(description = "Ngày và giờ bắt đầu (định dạng ISO 8601, ví dụ: 2024-01-10T09:00:00)", required = true)
            LocalDateTime startDate,

            @RequestParam @NotNull(message = "Ngày kết thúc là bắt buộc")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            @Parameter(description = "Ngày và giờ kết thúc (định dạng ISO 8601, ví dụ: 2025-01-01T09:00:00)", required = true)
            LocalDateTime endDate,

            @RequestParam @NotNull(message = "Định dạng là bắt buộc")
            @Parameter(description = "Định dạng file (pdf, csv, excel)", required = true, example = "pdf")
            String format,

            @RequestParam(defaultValue = "false")
            @Parameter(description = "Lưu file vào hệ thống tệp (true/false)", example = "false")
            boolean saveToFileSystem) throws IOException {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu");
        }

        List<TransactionStatisticsResponse> stats = statisticsService.getTransactionStatistics(startDate, endDate);
        byte[] fileContent = exportContext.export(format, stats, startDate, endDate);
        String fileName = exportContext.getFileName(format);
        String contentType = exportContext.getContentType(format);

        exportContext.saveToDatabase(format, fileContent, startDate, endDate);

        if (saveToFileSystem) {
            String filePath = exportContext.saveToFileSystem(format, fileContent);
            System.out.println("File được lưu tại: " + filePath);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(contentType))
                .body(fileContent);
    }
}