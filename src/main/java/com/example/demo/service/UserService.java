package com.example.demo.service;

import com.example.demo.model.Competence;
import com.example.demo.model.User;
import com.example.demo.repository.CompetenceRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private CompetenceRepository competenceRepository;

    public UserService(UserRepository productRepository) {
        this.userRepository = productRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//    public User signInUser(User user) {
//        String email = user.getEmail();
//        String password = user.getPassword();
//
//        // Find the user by email
//        User existingUser = userRepository.findByEmail(email);
//
//        if (existingUser == null) {
//            // Handle the case when the user is not found
//            throw new RuntimeException("User not found");
//        }
//
//        // Directly compare the plain-text passwords
//        if (!password.equals(existingUser.getPassword())) {
//            throw new RuntimeException("Wrong password");
//        }
//
//        return existingUser;
//    }
/// //////////////////
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
/// ////////////////////////////
    //khaoulaaaaaaaaaaa

    public List<User> getAllDevelopers() {
        return userRepository.findByRole("developer");
    }



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
            Competence competence = competenceRepository.findByCompetenceName(competenceName)
                    .orElseGet(() -> {
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


    }









