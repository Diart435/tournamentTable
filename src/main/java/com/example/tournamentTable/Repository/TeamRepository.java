package com.example.tournamentTable.Repository;

import com.example.tournamentTable.Entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    Optional<Team> findByTitle(String title);
    boolean existsByTitle(String title);
}
