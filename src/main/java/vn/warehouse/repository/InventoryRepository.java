package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Inventory;
import vn.warehouse.model.User;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
