package com.example.tournamentTable.Repository;

import com.example.tournamentTable.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Optional<Player> findByName(String name);
    @Query("SELECT p FROM Player p WHERE p.team IS NOT NULL")
    List<Player> findAllWithTeam();
    @Query("SELECT p FROM Player p WHERE p.team IS NULL")
    List<Player> findAllWithoutTeam();
    boolean existsByName(String name);
}
