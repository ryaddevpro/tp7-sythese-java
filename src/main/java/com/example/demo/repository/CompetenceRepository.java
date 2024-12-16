package com.example.demo.repository;

import com.example.demo.model.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    // Fetch all competences with the same name
//    List<Competence> findB(String name);  // Returns list of competences

    // Fetch a competence by name assuming it's unique (returns Optional)

    // Custom query to ensure uniqueness using @Query
    // This query will ensure we only get one competence or handle errors if multiple records exist

    Optional<Competence> findByCompetenceName(String competenceName);
}
