package com.example.demo.repository;

import com.example.demo.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    // Fetch evaluations where the evaluator or developer matches the given email
    @Query("SELECT e FROM evaluation e WHERE e.evaluator.email = :email OR e.developer.email = :email")
    List<Evaluation> findByUserEmail(String email);
}
