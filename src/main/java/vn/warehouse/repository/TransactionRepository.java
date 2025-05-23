package vn.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.warehouse.dto.response.TransactionStatisticsResponse;
import vn.warehouse.model.Transaction;
import vn.warehouse.model.User;
import vn.warehouse.model.enumuration.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByType(TransactionType type, Pageable pageable);
    Page<Transaction> findByStatus(String status, Pageable pageable);
    Page<Transaction> findByTypeAndStatus(TransactionType type, String status, Pageable pageable);
    Page<Transaction> findByEmployeeId(Long employeeId, Pageable pageable);

    @Query("SELECT new vn.warehouse.dto.response.TransactionStatisticsResponse(" +
            "t.type, t.status, COUNT(t), SUM(t.quantity)) " +
            "FROM Transaction t " +
            "WHERE t.transactionDate BETWEEN :startDate AND :endDate " +
            "GROUP BY t.type, t.status")
    List<TransactionStatisticsResponse> getTransactionStatistics(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t FROM Transaction t WHERE " +
            "(:startDate IS NULL OR t.transactionDate >= :startDate) AND " +
            "(:endDate IS NULL OR t.transactionDate <= :endDate)")
    List<Transaction> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
}
