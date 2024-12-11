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
@Entity(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;


    @ManyToMany
    @JoinTable(name = "project_comptence", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "competence_id"))
    private Set<Competence> competences = new HashSet<Competence>();


}
