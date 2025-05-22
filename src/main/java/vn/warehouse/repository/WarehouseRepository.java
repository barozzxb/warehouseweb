package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Warehouse;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
