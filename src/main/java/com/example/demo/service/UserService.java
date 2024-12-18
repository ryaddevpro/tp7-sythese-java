package com.example.demo.service;

import com.example.demo.model.Competence;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.CompetenceRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CompetenceRepository competenceRepository;
    private final ProjectRepository projectRepository;

    // Constructor-based dependency injection
    public UserService(UserRepository userRepository, CompetenceRepository competenceRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.competenceRepository = competenceRepository;
        this.projectRepository = projectRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User saveUser(User user) {
        // Check if the email already exists in the database
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        // Save the user if the email is unique
        return userRepository.save(user);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public User signInUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        // Find the user by email
        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        // Check if the provided password matches
        if (!password.equals(existingUser.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return existingUser;
    }
    //khaoulaaaaaaaaaaa



    // Recherche d'utilisateurs par compétences
    public List<User> findUsersByCompetences(List<String> competences) {
        return userRepository.findUsersByCompetences(competences);
    }


    public void addUserWithCompetences(String name, String email, String password, String role, List<String> competenceNames) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        // Trouver ou créer les compétences
        Set<Competence> competences = new HashSet<>();
        for (String competenceName : competenceNames) {
            Competence competence = competenceRepository.findByCompetenceName(competenceName).orElseGet(() -> {
                Competence newCompetence = new Competence();
                newCompetence.setCompetenceName(competenceName);
                return competenceRepository.save(newCompetence);
            });
            competences.add(competence);
        }

        user.setCompetences(competences);

        // Sauvegarder l'utilisateur et les relations dans la table user_competence
        userRepository.save(user);
    }

    public User updateUserByEmail(String email, User updatedUser) {
        // Find the user by email
        User userFromDb = userRepository.findByEmail(email);

        if (userFromDb == null) {
            throw new RuntimeException("User not found");
        }

        // Update the user's details
        userFromDb.setName(updatedUser.getName());
        userFromDb.setEmail(updatedUser.getEmail());
        userFromDb.setRole(updatedUser.getRole());

        // Save updated user details
        return userRepository.save(userFromDb);
    }


    public void assignCompetenceToUser(Long userId, List<Long> competenceIds) {
        // Find the user by ID
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch the competences by their IDs
        Set<Competence> competences = new HashSet<>();
        for (Long competenceId : competenceIds) {
            Competence competence = competenceRepository.findById(competenceId)
                    .orElseThrow(() -> new RuntimeException("Competence not found"));
            competences.add(competence);
        }

        // Assign competences to the user
        user.getCompetences().addAll(competences);

        // Save the updated user
        userRepository.save(user);
    }
    public Set<Competence> getCompetencesByUserId(Long userId) {
        return userRepository.findCompetencesByUserId(userId);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getAllDevelopers() {
        List<User> developers = userRepository.findByRole("developpeur");
        return developers;
    }

    public void assignProjectToUser(Long userId, Long projectId, List<Long> competenceIds) {
        // Find the user by ID
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Find the project by ID
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        // Fetch the competences by their IDs
        Set<Competence> competences = new HashSet<>();
        for (Long competenceId : competenceIds) {
            Competence competence = competenceRepository.findById(competenceId).orElseThrow(() -> new RuntimeException("Competence not found with ID: " + competenceId));
            competences.add(competence);
        }

        // Assign competences to the project
        project.getCompetences().addAll(competences);

        // Assign the project to the user
        user.getProjects().add(project);

        // Save the updated project and user
        projectRepository.save(project);
        userRepository.save(user);
    }

    public List<User> getAllUsersWithProjectsAndCompetences() {
        return this.userRepository.findAllUsersWithProjectsAndCompetences();
    }

}









