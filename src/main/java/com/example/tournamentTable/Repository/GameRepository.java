package com.example.tournamentTable.Repository;

import com.example.tournamentTable.Entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

}
