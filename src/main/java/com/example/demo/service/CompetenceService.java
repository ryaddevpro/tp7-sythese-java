package com.example.demo.service;

import com.example.demo.model.Competence;
import com.example.demo.repository.CompetenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetenceService {

    private final CompetenceRepository competenceRepository;

    public CompetenceService(CompetenceRepository competenceRepository) {
        this.competenceRepository = competenceRepository;
    }

    // Méthode pour récupérer toutes les compétences
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    // Méthode pour enregistrer une compétence
    public void saveCompetence(Competence competence) {
        competenceRepository.save(competence);
    }

    // Méthode pour supprimer une compétence par son ID
    public void deleteCompetence(Long competenceId) {
        competenceRepository.deleteById(competenceId);
    }

    // Méthode pour récupérer une compétence par son ID
    public Competence getCompetenceById(Long competenceId) {
        return competenceRepository.findById(competenceId).orElse(null); // Retourne null si la compétence n'est pas trouvée
    }
    // Méthode dans CompetenceService
    // Méthode dans CompetenceService
    public List<Competence> getCompetencesByIds(List<Long> competenceIds) {
        return competenceRepository.findAllById(competenceIds);
    }


}
