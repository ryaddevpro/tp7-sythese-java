package com.example.demo.controller.Project;

import com.example.demo.model.Competence;
import com.example.demo.service.CompetenceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CompetenceController {

    private final CompetenceService competenceService;

    public CompetenceController(CompetenceService competenceService) {
        this.competenceService = competenceService;
    }

    // Afficher la page pour ajouter une compétence
    @GetMapping("/addCompetence")
    public String showAddCompetencePage(Model model) {
        model.addAttribute("competence", new Competence());
        return "Project/addCompetence"; // Vue correspondante (HTML)
    }

    // Ajouter une compétence après soumission du formulaire
    @PostMapping("/addCompetence")
    public String addCompetence(@ModelAttribute("competence") Competence competence, Model model) {
        // Sauvegarder la compétence dans la base de données
        competenceService.saveCompetence(competence);

        // Ajouter un message de succès
        model.addAttribute("message", "Compétence ajoutée avec succès !");
        model.addAttribute("messageType", "success"); // Pour le style

        // Renvoyer à la même page
        return "Project/addCompetence";
    }

    // Afficher toutes les compétences
    @GetMapping("/Competence")
    public String showCompetences(Model model) {
        // Récupérer toutes les compétences
        model.addAttribute("competences", competenceService.getAllCompetences());
        return "Project/Competence"; // Vue qui affiche les compétences
    }

    // Supprimer une compétence par son ID
    @GetMapping("/deleteCompetence/{id}")
    public String deleteCompetence(@PathVariable("id") Long competenceId, RedirectAttributes redirectAttributes) {
        // Supprimer la compétence
        competenceService.deleteCompetence(competenceId);

        // Ajouter un message de succès
        redirectAttributes.addFlashAttribute("message", "Compétence supprimée avec succès !");
        redirectAttributes.addFlashAttribute("messageType", "success");

        // Rediriger vers la liste des compétences
        return "redirect:updateCompetence";
    }

    // Afficher la page de mise à jour pour une compétence
    @GetMapping("/updateCompetence/{id}")
    public String showUpdateCompetencePage(@PathVariable("id") Long competenceId, Model model) {
        // Récupérer la compétence par son ID
        Competence competence = competenceService.getCompetenceById(competenceId);

        // Ajouter la compétence au modèle
        model.addAttribute("competence", competence);

        // Rediriger vers la page de mise à jour
        return "Project/updateCompetence";
    }

    // Mettre à jour une compétence
    @PostMapping("/updateCompetence")
    public String updateCompetence(@ModelAttribute("competence") Competence competence, Model model) {
        // Sauvegarder les modifications
        competenceService.saveCompetence(competence);

        // Ajouter un message de succès
        model.addAttribute("message", "Compétence mise à jour avec succès !");
        model.addAttribute("messageType", "success");

        // Rediriger vers la liste des compétences
        return "redirect:/competence";
    }
}
