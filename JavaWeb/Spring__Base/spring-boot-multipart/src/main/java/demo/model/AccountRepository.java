package demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by olga on 17.07.15.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
}
