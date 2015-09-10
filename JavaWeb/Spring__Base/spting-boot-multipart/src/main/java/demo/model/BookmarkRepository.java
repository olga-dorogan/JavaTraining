package demo.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by olga on 17.07.15.
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByAccountUsername(String username);
}
