package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Transaction;
import vn.warehouse.model.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
