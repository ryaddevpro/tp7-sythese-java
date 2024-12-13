package com.example.demo.controller.user;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/sign-in";
        } else return "index";


    }

    @GetMapping("/sign-in")
    public String GetSignUp(Model model) {
        model.addAttribute("user", new User());

        return "user/sign-in";
    }

    @PostMapping("/")
    public String PostSignUp(@ModelAttribute("user") User user, Model model, HttpSession session) {
        try {
            User loggedIn = this.userService.signInUser(user);
            session.setAttribute("user", loggedIn);
            return "redirect:/";
        } catch (RuntimeException e) {
            System.out.println("errrrrrrrrrrrooooooooooooooooorrrrrrrrrrrrrrrrrrrrrrrrrr");
            model.addAttribute("error", "Wrong password or email");
            return "user/sign-in";

        }
    }


    @GetMapping("register")
    public String GetRegister(Model model) {
        model.addAttribute("user", new User());

        return "user/register";
    }


    @PostMapping("/register")
    public String PostRegister(User user, @ModelAttribute("user") User myUser) {
        System.out.println(user.toString());
        this.userService.saveUser(user);
        return "redirect:/sign-in";

    }

//    //khaoulaaaaaaaaaaaaaaaaaaa

    @GetMapping("/search")
    public String showSearchPage() {
        return "Project/search";
    }


    @PostMapping("/search")
    public String searchUsersByCompetences(
            @RequestParam("competences") String competencesInput,
            Model model) {

        // Vérification si l'entrée utilisateur est valide
        if (competencesInput != null && !competencesInput.trim().isEmpty()) {
            // Diviser les compétences entrées par l'utilisateur
            String[] competencesArray = competencesInput.split(",");
            List<String> competences = Arrays.stream(competencesArray)
                    .map(String::trim) // Supprime les espaces
                    .toList();

            // Recherche des utilisateurs par compétences
            List<User> users = userService.findUsersByCompetences(competences);
            model.addAttribute("users", users);
        } else {
            model.addAttribute("users", null);
        }

        return "Project/search"; // Retourne les résultats à la même page
    }


}