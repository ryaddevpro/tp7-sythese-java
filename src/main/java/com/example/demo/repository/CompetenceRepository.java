package com.example.demo.repository;

import com.example.demo.model.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    Optional<Competence> findByCompetenceName(String competenceName);
}
