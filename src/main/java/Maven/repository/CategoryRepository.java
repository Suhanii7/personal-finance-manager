package Maven.repository;

import Maven.entity.Category;
import Maven.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Finds categories that are either default (user is null) or belong to the specific user 
    List<Category> findByUserIsNull() ;
    List<Category> findByUser(User user);
    Optional<Category> findByNameAndUser(String name, User user);
    Optional<Category> findByNameAndUserIsNull(String name);
}