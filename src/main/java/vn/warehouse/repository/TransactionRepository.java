package vn.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Transaction;
import vn.warehouse.model.User;
import vn.warehouse.model.enumuration.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByType(TransactionType type, Pageable pageable);
    Page<Transaction> findByStatus(String status, Pageable pageable);
    Page<Transaction> findByTypeAndStatus(TransactionType type, String status, Pageable pageable);
    Page<Transaction> findByEmployeeId(Long employeeId, Pageable pageable);
}
