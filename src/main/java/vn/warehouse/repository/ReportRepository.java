package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}