package com.example.demo.controller.user;

import com.example.demo.model.Evaluation;
import com.example.demo.model.User;
import com.example.demo.service.EvaluationService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addEvaluation(@RequestParam Long developerId,
                                @RequestParam int note,
                                @RequestParam String comment,
                                HttpSession session,
                                Model model) {
        User developer = userService.getUserById(developerId);

        System.out.println(developer+"sdhjfkjalkdiwudolijqwe");

        if (developer != null) {
            // Créer une nouvelle évaluation
            Evaluation evaluation = new Evaluation();
            evaluation.setDeveloper(developer); // Associer le développeur
            evaluation.setRating(note);
            evaluation.setCommentaire(comment);
            User loggedInUser = (User) session.getAttribute("user");

            evaluation.setEvaluationDate(LocalDateTime.now());
            evaluation.setEvaluator(loggedInUser);
            evaluationService.saveEvaluation(evaluation);

            model.addAttribute("successMessage", "Évaluation ajoutée avec succès !");
        } else {
            model.addAttribute("errorMessage", "Erreur : Développeur introuvable.");
        }

        return "redirect:/ChefPagw"; // Redirection vers le tableau de bord
    }





}
