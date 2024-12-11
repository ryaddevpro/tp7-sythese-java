package com.example.demo.controller.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String getProfile(@RequestParam String email, Model model) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(User user) {
        userRepository.save(user);
        return "redirect:/profile?email=" + user.getEmail();
    }
}
