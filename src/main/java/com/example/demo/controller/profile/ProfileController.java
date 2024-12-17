package com.example.demo.controller.profile;

import com.example.demo.model.Competence;
import com.example.demo.model.User;
import com.example.demo.service.CompetenceService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ProfileController {

    private final UserService userService;
    private final CompetenceService competenceService;

    public ProfileController(UserService userService, CompetenceService competenceService) {
        this.userService = userService;
        this.competenceService = competenceService;
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session) {
        // Retrieve the logged-in user from the session
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            return "redirect:/sign-in";
        }

        // Fetch the latest user data from the database
        User userFromDb = userService.findById(sessionUser.getUserId());
        if (userFromDb == null) {
            session.invalidate(); // Invalid session if user is not found
            return "redirect:/sign-in";
        }

        // Fetch the list of competences for the logged-in user
        Set<Competence> competences = userService.getCompetencesByUserId(userFromDb.getUserId());
        List<Competence> allCompetences = competenceService.getAllCompetences();

        // Add the user, assigned competences, and all competences to the model for rendering
        model.addAttribute("user", userFromDb);
        model.addAttribute("competences", allCompetences); // All competences for selection
        model.addAttribute("assignedCompetences", competences); // Assigned competences to show

        return "profile/profile";
    }





    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("user") User updatedUser, @RequestParam(value = "competenceIds", defaultValue = "") List<Long> competenceIds, HttpSession session, Model model) {
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            return "redirect:/sign-in";
        }

        try {
            // Update user details by email
            User userFromDb = userService.updateUserByEmail(sessionUser.getEmail(), updatedUser);

            // Handle if no competences are selected
            if (competenceIds.isEmpty()) {
                competenceIds = new ArrayList<>();  // Set to empty list if not provided
            }

            // Add selected competences to the user's competences
            userService.assignCompetenceToUser(userFromDb.getUserId(), competenceIds);

            // Update the session user with the latest details
            session.setAttribute("user", userFromDb);

            return "redirect:/profile";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "profile/profile"; // This will re-render the profile page with an error message
        }
    }


}
