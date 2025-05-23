package vn.warehouse.service;

import vn.warehouse.dto.response.TransactionStatisticsResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface StatisticsService {
    List<TransactionStatisticsResponse> getTransactionStatistics(LocalDateTime startDate, LocalDateTime endDate);
}
