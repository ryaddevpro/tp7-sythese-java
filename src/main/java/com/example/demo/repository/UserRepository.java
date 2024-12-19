package com.example.demo.repository;
import com.example.demo.model.Competence;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    /// /////khaoulaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa/////////
    List<User> findByRole(String role);


    // Recherche d'utilisateurs par compétences

    @Query("SELECT u.competences FROM user u WHERE u.userId = :userId")
    Set<Competence> findCompetencesByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT u FROM user u JOIN u.competences c WHERE c.competenceName IN :competences")
    List<User> findUsersByCompetences(@Param("competences") List<String> competences);


    // Fetch all users with their projects and the competences related to those projects
    @Query("SELECT u FROM user u " +
            "LEFT JOIN FETCH u.projects p " +
            "LEFT JOIN FETCH p.competences c")
    List<User> findAllUsersWithProjectsAndCompetences();

    @Query("SELECT u FROM user u JOIN u.projects p WHERE p.projectId = :projectId")
    List<User> findUsersByProjectId(@Param("projectId") Long projectId);

}