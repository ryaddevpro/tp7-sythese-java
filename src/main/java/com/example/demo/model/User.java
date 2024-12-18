package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @ManyToMany
    @JoinTable(name = "user_competence",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id"))
    private Set<Competence> competences = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private final Set<Project> projects = new HashSet<>();

    // Ajout des relations pour Evaluation
    @OneToMany(mappedBy = "evaluator", cascade = CascadeType.ALL)
    private Set<Evaluation> evaluationsGiven = new HashSet<>(); // Évaluations faites par le chef de projet

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
    private Set<Evaluation> evaluationsReceived = new HashSet<>(); // Évaluations reçues par le développeur
}
