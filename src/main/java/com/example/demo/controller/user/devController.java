package com.example.demo.controller.user;


import com.example.demo.model.Evaluation;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EvaluationService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class devController {

    private final UserService userService;
    private final EvaluationService evaluationService;
    @Autowired
    private UserRepository userRepository;

    public devController(UserService userService, UserRepository userRepository, EvaluationService evaluationService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.evaluationService = evaluationService;
    }

    @GetMapping("/Project/dev")
    public String dev(Model model, HttpSession session) {

        // Assuming there's a logged-in user for this example
        User user = (User) session.getAttribute("user");
        User loggedInUser = this.userService.signInUser(user);

        // Fetch user with their relationships
        User users = userRepository.findById(loggedInUser.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch evaluations based on email
        String email = loggedInUser.getEmail(); // Ensure `getEmail` exists in User class
        List<Evaluation> evaluations = evaluationService.getEvaluationsByEmail(email);

        // Add details to the model
        model.addAttribute("developer", users);
        model.addAttribute("projects", users.getProjects());
        model.addAttribute("evaluations", evaluations);
        return "Project/dev";
    }

}
