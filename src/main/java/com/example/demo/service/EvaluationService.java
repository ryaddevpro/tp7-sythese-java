package com.example.demo.service;

import com.example.demo.model.Evaluation;
import com.example.demo.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public void saveEvaluation(Evaluation evaluation) {
        evaluationRepository.save(evaluation);
    }
}
