package com.example.demo.controller.Project;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class ProjectController {


    private final ProjectService projectService;

    private final UserService userService;


    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    // Bujujan
//    @GetMapping("/chefProfile")
//    public String viewProfile(Model model, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//
//        if (user == null) {
//            return "redirect:/sign-in";
//        }
//
//        model.addAttribute("user", user);
//        return "profile/profile";
//    }


    // Afficher la page pour ajouter un projet
    @GetMapping("/addProject")
    public String showAddProjectPage(Model model) {
        model.addAttribute("project", new Project());
        return "project/addProject";
    }


    // Ajouter un projet après la soumission du formulaire
    @PostMapping("/addProject")
    public String addProject(@ModelAttribute("project") Project project, Model model) {
        // Sauvegarder le projet dans la base de données
        projectService.saveProject(project);

        // Ajouter un message de succès directement dans le modèle
        model.addAttribute("message", "Projet ajouté avec succès !");
        model.addAttribute("messageType", "success"); // Pour gérer la couleur (vert)

        // Renvoyer à la même page avec un message de succès
        return "project/addProject";
    }



    @GetMapping("/projects")
    public String showProjects(Model model) {
        // Récupérer tous les projets depuis la base de données
        model.addAttribute("projects", projectService.getAllProjects());
        return "project/projects";  // Rediriger vers la page qui affiche la liste des projets
    }

    @GetMapping("/deleteProject/{id}")
    public String deleteProject(@PathVariable("id") Long projectId, RedirectAttributes redirectAttributes) {
        // Supprimer le projet
        projectService.deleteProject(projectId);

        // Ajouter un message de succès pour la suppression
        redirectAttributes.addFlashAttribute("message", "Projet supprimé avec succès !");
        redirectAttributes.addFlashAttribute("messageType", "success"); // Pour gérer la couleur (vert)

        // Rediriger vers la page des projets
        return "redirect:/projects";
    }

    @GetMapping("/updateProject/{id}")
    public String showUpdateProjectPage(@PathVariable("id") Long projectId, Model model) {
        // Récupérer le projet par son ID
        Project project = projectService.getProjectById(projectId);

        // Ajouter le projet au modèle
        model.addAttribute("project", project);

        // Rediriger vers la page de mise à jour
        return "project/updatePage";
    }


/// /////////////////////////////////////////////////////////////////////////////////

    /////////////////////////
    @PostMapping("/updateProject")
    public String updateProject(@ModelAttribute("project") Project project, Model model) {
        // Sauvegarder les modifications du projet
        projectService.saveProject(project);

        // Ajouter un message de succès
        model.addAttribute("message", "Projet mis à jour avec succès !");
        model.addAttribute("messageType", "success");

        // Rediriger vers la liste des projets
        return "redirect:/projects";
    }

/// /////////bujujan
@GetMapping("/chefProfile")
public String viewProfile(Model model, HttpSession session) {
    User user = (User) session.getAttribute("user");

    if (user == null) {
        return "redirect:/sign-in";
    }

    model.addAttribute("user", user);
    return "profile/profile";
}
}

