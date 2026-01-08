package Maven.controller;

import Maven.entity.*;
import Maven.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; // Missing import
import org.springframework.http.HttpStatus;     // Added for safety
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired private TransactionRepository transactionRepo;
    @Autowired private UserRepository userRepo;

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<?> getMonthlyReport(@PathVariable int year, @PathVariable int month) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(email).orElseThrow();

        List<Transaction> transactions = transactionRepo.findByUserOrderByDateDesc(user).stream()
            .filter(t -> t.getDate().getYear() == year && t.getDate().getMonthValue() == month)
            .collect(Collectors.toList());

        return ResponseEntity.ok(generateReportMap(year, month, transactions));
    }

    @GetMapping("/yearly/{year}")
    public ResponseEntity<?> getYearlyReport(@PathVariable int year) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(email).orElseThrow();

        List<Transaction> transactions = transactionRepo.findByUserOrderByDateDesc(user).stream()
            .filter(t -> t.getDate().getYear() == year)
            .collect(Collectors.toList());

        return ResponseEntity.ok(generateReportMap(year, 0, transactions));
    }

    private Map<String, Object> generateReportMap(int year, int month, List<Transaction> transactions) {
        Map<String, Double> incomeByCategory = transactions.stream()
            .filter(t -> t.getCategory().getType().equals("INCOME"))
            .collect(Collectors.groupingBy(t -> t.getCategory().getName(), Collectors.summingDouble(Transaction::getAmount)));

        Map<String, Double> expenseByCategory = transactions.stream()
            .filter(t -> t.getCategory().getType().equals("EXPENSE"))
            .collect(Collectors.groupingBy(t -> t.getCategory().getName(), Collectors.summingDouble(Transaction::getAmount)));

        double totalInc = incomeByCategory.values().stream().mapToDouble(d -> d).sum();
        double totalExp = expenseByCategory.values().stream().mapToDouble(d -> d).sum();

        return Map.of(
            "year", year,
            "totalIncome", incomeByCategory,
            "totalExpenses", expenseByCategory,
            "netSavings", totalInc - totalExp
        );
    }
}