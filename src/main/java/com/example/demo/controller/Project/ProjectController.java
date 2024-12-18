package com.example.demo.controller.Project;

import com.example.demo.model.Competence;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.CompetenceRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CompetenceService;
import com.example.demo.service.ProjectService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class ProjectController {


    private final ProjectService projectService;

    private final UserService userService;

    private final CompetenceService competenceService;
    private final CompetenceRepository competenceRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    public ProjectController(ProjectService projectService, UserService userService, CompetenceService competenceService, CompetenceRepository competenceRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectService = projectService;
        this.competenceService = competenceService;
        this.userService = userService;
        this.competenceRepository = competenceRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    // Afficher la page pour ajouter un projet
    @GetMapping("/addProject")
    public String showAddProjectPage(Model model) {
        model.addAttribute("project", new Project());


        List<User> developers = userService.getAllDevelopers();
        List<Competence> competences = projectService.getAllCompetences();
        model.addAttribute("comptences", competences);
        System.out.println("Liste des dev :" + developers);
        if (developers != null && !developers.isEmpty()) {
            System.out.println("Liste des dev :" + developers);
            model.addAttribute("developers", developers);
        }
        return "project/addProject";
    }


    // Ajouter un projet après la soumission du formulaire
    // Ajouter un projet après la soumission du formulaire
    @PostMapping("/addProject")
    public String addProject(
            @ModelAttribute("project") Project project,
            @RequestParam("competenceIds") List<Long> competenceIds,
            @RequestParam(value = "developers", required = false) List<Long> developerIds, // Optional developers
            Model model) {
        try {
            // Fetch and assign competences to the project
            Set<Competence> competences = new HashSet<>();
            for (Long competenceId : competenceIds) {
                Competence competence = this.competenceRepository.findById(competenceId)
                        .orElseThrow(() -> new RuntimeException("Competence not found with ID: " + competenceId));
                competences.add(competence);
            }
            project.setCompetences(competences);

            // Save the project
            Project savedProject = projectRepository.save(project);

            // Assign the project to developers if developerIds are provided
            if (developerIds != null && !developerIds.isEmpty()) {
                for (Long developerId : developerIds) {
                    User developer = this.userService.findById(developerId);
                    developer.getProjects().add(savedProject);
                    this.userService.assignProjectToUser(developerId, project.getProjectId(), competenceIds);
                }
            }

            // Success message
            model.addAttribute("message", "Projet ajouté avec succès !");
            model.addAttribute("messageType", "success"); // For styling (e.g., green color for success)
        } catch (Exception e) {
            // Error message
            model.addAttribute("message", "Erreur lors de l'ajout du projet : " + e.getMessage());
            model.addAttribute("messageType", "error"); // For styling (e.g., red color for error)
        }

        // Return to the same page with a message
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

