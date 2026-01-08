package Maven.service;

import Maven.entity.User;
import Maven.repository.UserRepository;
import Maven.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public User register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // We will add encryption in the security step
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());

        return userRepository.save(user);
    }
}