package vn.warehouse.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.warehouse.dto.response.TransactionStatisticsResponse;
import vn.warehouse.repository.TransactionRepository;
import vn.warehouse.service.StatisticsService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsServiceImpl implements StatisticsService {
    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionStatisticsResponse> getTransactionStatistics(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getTransactionStatistics(startDate, endDate);
    }
}
