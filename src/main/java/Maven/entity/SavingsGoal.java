package Maven.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "savings_goals")
@Data
public class SavingsGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goalName;
    private Double targetAmount;
    private LocalDate targetDate;
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}