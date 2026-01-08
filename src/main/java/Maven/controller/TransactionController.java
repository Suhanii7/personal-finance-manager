package Maven.controller;

import Maven.entity.*;
import Maven.service.FinanceService;
import Maven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired private FinanceService financeService;
    @Autowired private UserRepository userRepo;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> payload) {
        // Get the logged-in user's email from the security session 
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(email).orElseThrow();

        Transaction t = financeService.createTransaction(
            Double.parseDouble(payload.get("amount").toString()),
            payload.get("date").toString(),
            payload.get("category").toString(),
            (String) payload.get("description"),
            user
        );

        return new ResponseEntity<>(t, HttpStatus.CREATED); // [cite: 156]
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(email).orElseThrow();
        return ResponseEntity.ok(Map.of("transactions", financeService.getTransactions(user)));
    }
}