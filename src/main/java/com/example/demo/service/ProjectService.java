//package com.example.demo.service;
//
//import com.example.demo.model.Project;
//import com.example.demo.repository.ProjectRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
////
//@Service
//public class ProjectService {
//
//    private final ProjectRepository projectRepository;
//
//    public ProjectService(ProjectRepository projectRepository) {
//        this.projectRepository = projectRepository;
//    }
//
//    // Méthode pour enregistrer un projet
//    public void saveProject(Project project) {
//        projectRepository.save(project);
//    }
//
//    // Méthode pour récupérer tous les projets
//    public Iterable<Project> getAllProjects() {
//        return projectRepository.findAll();
//    }
//}
//
//package com.example.demo.service;
//
//import com.example.demo.model.Project;
//import com.example.demo.repository.ProjectRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ProjectService {
//
//    private final ProjectRepository projectRepository;
//
//    public ProjectService(ProjectRepository projectRepository) {
//        this.projectRepository = projectRepository;
//    }
//
//    // Méthode pour enregistrer un projet
//    public void saveProject(Project project) {
//        projectRepository.save(project);
//    }
//
//    // Méthode pour récupérer tous les projets
//    public Iterable<Project> getAllProjects() {
//        return projectRepository.findAll();
//    }
//}


package com.example.demo.service;

import com.example.demo.model.Competence;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.CompetenceRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CompetenceRepository competenceRepository;
    private final UserRepository userRepository;


    public ProjectService(ProjectRepository projectRepository, CompetenceRepository competenceRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.competenceRepository = competenceRepository;
        this.userRepository = userRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }



    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    // Méthode pour supprimer un projet par son ID
    public void deleteProject(Long projectId) {
        // Manually delete related user_project rows
        List<User> users = userRepository.findUsersByProjectId(projectId);
        for (User user : users) {
            user.getProjects().removeIf(project -> project.getProjectId().equals(projectId));
            userRepository.save(user);
        }
        // Then delete the project
        projectRepository.deleteById(projectId);
    }

    // Récupérer un projet par son ID
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);  // Retourne null si le projet n'est pas trouvé
    }

    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }
}
