package Maven.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class GoalResponse {
    private Long id;
    private String goalName;
    private Double targetAmount;
    private LocalDate targetDate;
    private LocalDate startDate;
    private Double currentProgress;
    private Double progressPercentage;
    private Double remainingAmount;
}