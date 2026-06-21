package com.example.tournamentTable.Repository;

import com.example.tournamentTable.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByTitle(String title);
    boolean existsByTitle(String title);
    @Query("SELECT t FROM Team t ORDER BY t.teamScore DESC")
    List<Team> findAll();
}
