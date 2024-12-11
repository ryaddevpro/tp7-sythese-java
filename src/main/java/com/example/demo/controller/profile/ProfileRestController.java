package com.example.demo.controller.profile;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileRestController {

    @Autowired
    private UserRepository userRepository;

    // Fetch user profile by email
    @GetMapping
    public ResponseEntity<User> getUserProfile(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    // Update user profile by email
    @PutMapping
    public ResponseEntity<User> updateUserProfile(@RequestParam String email, @RequestBody User updatedUser) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
