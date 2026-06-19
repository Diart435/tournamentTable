package com.example.tournamentTable.Controller;

import com.example.tournamentTable.DTO.GameDTO;
import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Entity.Team;
import com.example.tournamentTable.Service.GameService;
import com.example.tournamentTable.Service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping("/public/get/games")
    public ResponseEntity<List<Game>> getGames(){
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    @PostMapping("/private/add/game")
    public ResponseEntity<Void> addGame(@Valid @RequestBody GameDTO gameDTO){
        gameService.addGame(gameDTO.getTeam1(), gameDTO.getTeam2(), gameDTO.getScore1(), gameDTO.getScore2());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
