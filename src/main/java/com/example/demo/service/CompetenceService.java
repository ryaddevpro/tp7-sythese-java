package com.example.demo.service;

import com.example.demo.model.Competence;
import com.example.demo.repository.CompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    // Constructor-based dependency injection
    public CompetenceService(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    // Méthode pour récupérer toutes les compétences
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    // Méthode pour enregistrer une compétence avec vérification d'existence
    public String saveCompetence(Competence competence) {
        // Check if a competence with the same name already exists
        Optional<Competence> existingCompetence = competenceRepository.findByCompetenceName(competence.getCompetenceName());

        if (existingCompetence.isPresent()) {
            return "Competence with the nam" + "e '" + competence.getCompetenceName() + "' already exists!";
        }

        // Save the competence if it doesn't already exist
        competenceRepository.save(competence);
        return "Competence added successfully!";
    }

    // Méthode pour supprimer une compétence par son ID
    public void deleteCompetence(Long competenceId) {
        competenceRepository.deleteById(competenceId);
    }

    // Méthode pour récupérer une compétence par son ID
    public Competence getCompetenceById(Long competenceId) {
        return competenceRepository.findById(competenceId).orElse(null); // Retourne null si la compétence n'est pas trouvée
    }

    // Méthode pour récupérer plusieurs compétences par leurs IDs
    public List<Competence> getCompetencesByIds(List<Long> competenceIds) {
        return competenceRepository.findAllById(competenceIds);
    }
}
