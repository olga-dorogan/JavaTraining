package demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by olga on 17.07.15.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
