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

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }



    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    // Méthode pour supprimer un projet par son ID
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }
    // Récupérer un projet par son ID
    public Project getProjectById(Long projectId) {
        return projectRepository.findById(projectId).orElse(null);  // Retourne null si le projet n'est pas trouvé
    }

}


