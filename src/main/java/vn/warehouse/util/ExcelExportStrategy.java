package vn.warehouse.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import vn.warehouse.dto.response.TransactionStatisticsResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ExcelExportStrategy implements ExportStrategy {
    @Override
    public byte[] export(List<TransactionStatisticsResponse> stats, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thống kê giao dịch");

        // Tạo header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Loại giao dịch", "Trạng thái", "Tổng số giao dịch", "Tổng số lượng"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Điền dữ liệu
        int rowNum = 1;
        for (TransactionStatisticsResponse stat : stats) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stat.getType().toString());
            row.createCell(1).setCellValue(stat.getStatus());
            row.createCell(2).setCellValue(stat.getTotalTransactions());
            row.createCell(3).setCellValue(stat.getTotalQuantity());
        }

        // Tự động điều chỉnh kích thước cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        return baos.toByteArray();
    }

    @Override
    public String getFileName() {
        return "transaction-statistics.xlsx";
    }

    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }
}