package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    private Long evaluationId;

    @ManyToOne
    @JoinColumn(name = "evaluator_id", referencedColumnName = "user_id", nullable = false)
    private User evaluator; // Chef de projet qui attribue la note

    @ManyToOne
    @JoinColumn(name = "developer_id", referencedColumnName = "user_id", nullable = false)
    private User developer; // Développeur qui reçoit la note

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate;
}
