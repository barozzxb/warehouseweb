package vn.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.warehouse.model.User;
import vn.warehouse.model.enumuration.Role;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Page<User> findByUsernameContainingIgnoreCaseOrPhoneContaining(String search, String search1, Pageable pageable);

    Page<User> findByUsernameContainingIgnoreCaseOrPhoneContainingAndRole(String search, String search1, Role role, Pageable pageable);

    Page<User> findByRole(Role role, Pageable pageable);
}
