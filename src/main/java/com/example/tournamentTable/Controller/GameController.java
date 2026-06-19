package com.example.tournamentTable.Controller;

import com.example.tournamentTable.Entity.Game;
import com.example.tournamentTable.Service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping("/public/getGames")
    public ResponseEntity<List<Game>> getGames(){
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }
}
