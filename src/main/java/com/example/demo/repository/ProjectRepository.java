package com.example.demo.repository;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {



    @Modifying
    @Query(value = "DELETE FROM user_project WHERE project_id = :projectId", nativeQuery = true)
    void deleteAssociations(@Param("projectId") Long projectId);

    void deleteById(Long projectId); // Default JpaRepository method

}
