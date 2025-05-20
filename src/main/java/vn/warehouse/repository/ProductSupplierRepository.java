package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.ProductSupplier;
import vn.warehouse.model.User;

@Repository
public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {
}
