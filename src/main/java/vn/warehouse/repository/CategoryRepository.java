package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.Category;
import vn.warehouse.model.User;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
