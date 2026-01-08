package Maven.controller;

import Maven.entity.*;
import Maven.dto.GoalResponse;
import Maven.repository.*;
import Maven.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/goals")
public class SavingsGoalController {
    @Autowired private SavingsGoalRepository goalRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private FinanceService financeService;

    @PostMapping
    public GoalResponse create(@RequestBody SavingsGoal goal) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(email).orElseThrow();
        
        goal.setUser(user);
        if (goal.getStartDate() == null) goal.setStartDate(LocalDate.now()); // [cite: 56]
        
        SavingsGoal saved = goalRepo.save(goal);
        return financeService.getGoalDetails(saved, user);
    }

    @GetMapping
    public List<GoalResponse> getAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(email).orElseThrow();
        return goalRepo.findByUser(user).stream()
                .map(g -> financeService.getGoalDetails(g, user))
                .collect(Collectors.toList());
    }
}