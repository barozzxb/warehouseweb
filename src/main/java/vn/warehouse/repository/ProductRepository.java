package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Product;
import vn.warehouse.model.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
