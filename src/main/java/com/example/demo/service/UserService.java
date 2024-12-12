package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository productRepository) {
        this.userRepository = productRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User signInUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        // Find the user by email
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            // Handle the case when the user is not found
            throw new RuntimeException("User not found");
        }

        // Directly compare the plain-text passwords
        if (!password.equals(existingUser.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return existingUser;
    }


    //khaoulaaaaaaaaaaa
    public List<User> getDevelopers() {
        return userRepository.findByRole("developer");
    }
}
