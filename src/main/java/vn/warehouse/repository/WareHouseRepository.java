package vn.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.User;
import vn.warehouse.model.WareHouse;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
}
