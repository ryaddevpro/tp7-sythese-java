package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "competence")
public class Competence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competence_id")
    private Long comptenceId;

    @Column(name = "competence_name")
    private String competenceName;

    @ManyToMany(mappedBy = "competences")
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "competences")
    private Set<User> users = new HashSet<>();
}
