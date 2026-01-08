package Maven.service;

import Maven.entity.*;
import Maven.repository.*;
import Maven.dto.GoalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class FinanceService {
    @Autowired private TransactionRepository transactionRepo;
    @Autowired private CategoryRepository categoryRepo;
    @Autowired private SavingsGoalRepository goalRepo;

    public Transaction createTransaction(Double amount, String date, String categoryName, String desc, User user) {
        LocalDate parsedDate = LocalDate.parse(date);
        if (parsedDate.isAfter(LocalDate.now())) {
            throw new RuntimeException("Date cannot be a future date");
        }

        Category category = categoryRepo.findByNameAndUser(categoryName, user)
                .orElseGet(() -> categoryRepo.findByNameAndUserIsNull(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found")));

        Transaction t = new Transaction();
        t.setAmount(amount);
        t.setDate(parsedDate);
        t.setCategory(category);
        t.setDescription(desc);
        t.setUser(user);
        return transactionRepo.save(t);
    }

    public List<Transaction> getTransactions(User user) {
        return transactionRepo.findByUserOrderByDateDesc(user);
    }

    public GoalResponse getGoalDetails(SavingsGoal goal, User user) {
        List<Transaction> transactions = transactionRepo.findByUserOrderByDateDesc(user);
        
        double totalIncome = transactions.stream()
            .filter(t -> t.getDate().isAfter(goal.getStartDate().minusDays(1)))
            .filter(t -> t.getCategory().getType().equals("INCOME"))
            .mapToDouble(Transaction::getAmount).sum();

        double totalExpense = transactions.stream()
            .filter(t -> t.getDate().isAfter(goal.getStartDate().minusDays(1)))
            .filter(t -> t.getCategory().getType().equals("EXPENSE"))
            .mapToDouble(Transaction::getAmount).sum();

        double currentProgress = totalIncome - totalExpense;
        
        GoalResponse res = new GoalResponse();
        res.setId(goal.getId());
        res.setGoalName(goal.getGoalName());
        res.setTargetAmount(goal.getTargetAmount());
        res.setTargetDate(goal.getTargetDate());
        res.setStartDate(goal.getStartDate());
        res.setCurrentProgress(currentProgress);
        res.setProgressPercentage(goal.getTargetAmount() > 0 ? (currentProgress / goal.getTargetAmount()) * 100 : 0);
        res.setRemainingAmount(Math.max(0, goal.getTargetAmount() - currentProgress));
        return res;
    }
}