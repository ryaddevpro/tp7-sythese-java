package com.example.demo.service;

import com.example.demo.model.Evaluation;
import com.example.demo.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public void saveEvaluation(Evaluation evaluation) {
        evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getEvaluationsByEmail(String email) {
        return evaluationRepository.findByUserEmail(email);
    }
}
