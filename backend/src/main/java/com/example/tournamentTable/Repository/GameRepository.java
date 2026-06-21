package com.example.tournamentTable.Repository;

import com.example.tournamentTable.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
    @Query("SELECT g FROM Game g WHERE g.team1.id = :teamId OR g.team2.id = :teamId")
    List<Game> findAllGamesForTeam(@Param("teamId") UUID teamId);
    List<Game> findAll();
    @Query("SELECT EXISTS (SELECT 1 FROM Game g WHERE g.team1.id = :teamId OR g.team2.id = :teamId)")
    boolean existsByTeam(@Param("teamId") UUID teamId);
    @Query("SELECT g FROM Game g WHERE g.matchDate = :date ORDER BY g.matchDate DESC")
    List<Game> findAllOnDate(@Param("date") LocalDate date);
}
