package Maven.controller;

import Maven.dto.RegisterRequest;
import Maven.entity.User;
import Maven.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User registeredUser = authService.register(request);
        // Matching the exact response format from the assignment [cite: 132]
        return new ResponseEntity<>(Map.of(
            "message", "User registered successfully",
            "userId", registeredUser.getId()
        ), HttpStatus.CREATED);
    }
}