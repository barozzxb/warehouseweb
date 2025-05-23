package vn.warehouse.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import vn.warehouse.dto.response.TransactionStatisticsResponse;
import vn.warehouse.model.Report;
import vn.warehouse.repository.ReportRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExportContext {
    private final PdfExportStrategy pdfExportStrategy;
    private final CsvExportStrategy csvExportStrategy;
    private final ExcelExportStrategy excelExportStrategy;
    private final ReportRepository reportRepository;
    private Map<String, ExportStrategy> strategies;
    private static final String FILE_SYSTEM_PATH = "uploads/reports/";

    @PostConstruct
    private void initStrategies() {
        strategies = new HashMap<>();
        strategies.put("pdf", pdfExportStrategy);
        strategies.put("csv", csvExportStrategy);
        strategies.put("excel", excelExportStrategy);
    }

    public ExportStrategy getStrategy(String format) {
        ExportStrategy strategy = strategies.get(format.toLowerCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Định dạng không hợp lệ. Sử dụng pdf, csv hoặc excel.");
        }
        return strategy;
    }

    public byte[] export(String format, List<TransactionStatisticsResponse> stats, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        return getStrategy(format).export(stats, startDate, endDate);
    }

    public String getFileName(String format) {
        return getStrategy(format).getFileName();
    }

    public String getContentType(String format) {
        return getStrategy(format).getContentType();
    }

    public void saveToDatabase(String format, byte[] fileContent, LocalDateTime startDate, LocalDateTime endDate) {
        String reportType = "TRANSACTION_STATISTICS_" + format.toUpperCase();

        Report report = Report.builder()
                .reportType(reportType)
                .generatedDate(LocalDateTime.now())
                .data(fileContent) // Lưu trực tiếp byte[]
                .build();
        reportRepository.save(report);
    }

    public String saveToFileSystem(String format, byte[] fileContent) throws IOException {
        File directory = new File(FILE_SYSTEM_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = getFileName(format);
        String filePath = FILE_SYSTEM_PATH + fileName;
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(fileContent);
        }
        return filePath;
    }
}