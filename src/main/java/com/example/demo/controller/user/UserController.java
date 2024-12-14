package com.example.demo.controller.user;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

//    @GetMapping("/")
//    public String index(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//
//        if (user == null) {
//            return "redirect:/sign-in";
//        } else return "index";
//
//    }
    /// /////////////////////////////////////////////

    @GetMapping("/")
    public String index(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/sign-in";
        }

        // Redirection selon le rôle de l'utilisateur
        if ("chefProject".equalsIgnoreCase(user.getRole())) {
            return "redirect:/Project/ChefPagw";
        } else if ("developpeur".equalsIgnoreCase(user.getRole())) {
            return "redirect:/dev/home";
        } else {
            return "redirect:/sign-in"; // Fallback pour les rôles inconnus
        }
    }
    /// ////////////////////////////////////////////

    @GetMapping("/sign-in")
    public String GetSignUp(Model model) {
        model.addAttribute("user", new User());

        return "user/sign-in";
    }

//    @PostMapping("/")
//    public String PostSignUp(@ModelAttribute("user") User user, Model model, HttpSession session) {
//        try {
//            User loggedIn = this.userService.signInUser(user);
//            session.setAttribute("user", loggedIn);
//            return "redirect:/";
//        } catch (RuntimeException e) {
//            System.out.println("errrrrrrrrrrrooooooooooooooooorrrrrrrrrrrrrrrrrrrrrrrrrr");
//            model.addAttribute("error", "Wrong password or email");
//            return "user/sign-in";
//
//        }
//    }
/// //////////////////////

@PostMapping("/sign-in")
public String postSignIn(@ModelAttribute("user") User user, Model model, HttpSession session) {
    try {
        // Tentative de connexion de l'utilisateur
        User loggedInUser = userService.signInUser(user);

        // Stocker l'utilisateur dans la session
        session.setAttribute("user", loggedInUser);

        // Redirection en fonction du rôle de l'utilisateur
        if ("chefProject".equalsIgnoreCase(loggedInUser.getRole())) {
            return "redirect:/Project/ChefPagw"; // Redirection vers la page du Chef
        } else if ("developpeur".equalsIgnoreCase(loggedInUser.getRole())) {
            return "redirect:/dev/home"; // Redirection vers la page du Développeur
        } else {
            // Si le rôle est inconnu ou non défini, redirige vers la page de connexion
            return "redirect:/sign-in";
        }

    } catch (RuntimeException e) {
        model.addAttribute("error", "Wrong email or password");
        return "user/sign-in"; // Renvoyer vers la page de connexion en cas d'erreur
    }
}


//////////////////////////////////////

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




    @GetMapping("/Project/ChefPagw")
    public String chefHome() {
        return "Project/ChefPagw";
    }

    @GetMapping("/dev/home")
    public String devHome() {
        return "dev/home";
    }








//    //khaoulaaaaaaaaaaaaaaaaaaa

//    @GetMapping("/ChefPagw")
//    public String showSearchPage() {
//        return "Project/ChefPagw";
//    }
/// ///////////////////////

    @PostMapping("/ChefPagw")
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

        return "/Project/ChefPagw"; // Retourne les résultats à la même page
    }
/// //logout
@GetMapping("/logout")
public String logout(HttpSession session) {
    // Invalider la session pour déconnecter l'utilisateur
    session.invalidate();
    // Rediriger vers la page de connexion
    return "redirect:/sign-in";
}

}



















