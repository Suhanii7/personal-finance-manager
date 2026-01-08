package Maven.repository;

import Maven.entity.Transaction;
import Maven.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserOrderByDateDesc(User user); // "Sorted by newest first" [cite: 35]
}