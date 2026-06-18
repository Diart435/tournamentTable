package com.example.tournamentTable.Repository;

import com.example.tournamentTable.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Optional<Player> findByName(String name);
}
