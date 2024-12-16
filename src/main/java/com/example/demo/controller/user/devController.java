package com.example.demo.controller.user;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class devController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/Project/dev")
    public String dev(Model model) {
        // Assuming there's a logged-in user with ID 1 for this example
        Long userId = 1L;

        // Fetch user with their relationships
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Add user details to the model
        model.addAttribute("developer", user);
        model.addAttribute("projects", user.getProjects());
        return "Project/dev";
    }

}
