package vn.warehouse.util;

import vn.warehouse.dto.response.TransactionResponse;
import vn.warehouse.dto.response.TransactionStatisticsResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface ExportStrategy {
    byte[] export(List<TransactionStatisticsResponse> stats, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
    String getFileName();
    String getContentType();
}