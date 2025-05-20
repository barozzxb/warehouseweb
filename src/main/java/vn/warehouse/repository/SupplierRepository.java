package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Supplier;
import vn.warehouse.model.User;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
