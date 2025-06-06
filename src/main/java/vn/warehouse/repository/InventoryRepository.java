package vn.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Inventory;
import vn.warehouse.model.User;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Page<Inventory> findByWarehouseId(Long id, Pageable pageable);
}
