package com.example.demo.repository;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
